package com.nerdysoft.taskmanager.service.impl;

import com.nerdysoft.taskmanager.entity.Task;
import com.nerdysoft.taskmanager.repository.TaskRepository;
import com.nerdysoft.taskmanager.entity.Role;
import com.nerdysoft.taskmanager.entity.User;
import com.nerdysoft.taskmanager.exception.EntityNotFoundException;
import com.nerdysoft.taskmanager.exception.ValidationException;
import com.nerdysoft.taskmanager.repository.UserRepository;
import com.nerdysoft.taskmanager.service.AdminService;
import com.nerdysoft.taskmanager.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static com.nerdysoft.taskmanager.entity.User.EMAIL_PATTERN;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Inject
    public AdminServiceImpl(UserRepository userRepository, TaskRepository taskRepository,
                            BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void createUser(User user) {
        if (user.getAdmin()) {
            user.setRoles(new HashSet<>(Arrays.asList(Role.ROLE_USER, Role.ROLE_ADMIN)));
        } else {
            user.setRoles(Collections.singleton(Role.ROLE_USER));
        }
        user.setEmail(user.getEmail().toLowerCase());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRegistered(LocalDate.now());
        userRepository.save(user);
        LOG.debug("Admin create user with email {}", user.getEmail());
    }

    @Override
    public User updateUser(Integer userId, User updated) {
        User user = userRepository.getOne(userId);
        if (user == null) {
            LOG.error("User with id {} not found", userId);
            throw new EntityNotFoundException(String.format("User with id %d not found", userId));
        }
        if (updated.getAdmin() && !user.getRoles().contains(Role.ROLE_ADMIN)) {
            user.getRoles().add(Role.ROLE_ADMIN);
        } else {
            user.setRoles(Collections.singleton(Role.ROLE_USER));
        }
        user.setUserName(updated.getUserName());
        user.setUserLastName(updated.getUserLastName());
        user.setPassword(bCryptPasswordEncoder.encode(updated.getPassword()));
        user.setEmail(updated.getEmail().toLowerCase());
        user.setEnabled(updated.getEnabled());
        user.setAdmin(updated.getAdmin());
        LOG.debug("Admin update user with id {}", userId);
        emailService.sendEmail(user.getEmail(), "Admin updated your profile in Tusk Manager");
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.getOne(userId);
        if (user == null) {
            LOG.error("User with id {} not found", userId);
            throw new EntityNotFoundException(String.format("User with id %d not found", userId));
        }
        user.setTasks(Collections.emptyList());
        userRepository.save(user);
        userRepository.delete(user);
        emailService.sendEmail(user.getEmail(), "Admin deleted your profile in Tusk Manager");
        LOG.debug("Admin delete user with id {}", userId);
    }

    @Override
    public void suspendUser(Integer userId) {
        User user = userRepository.getOne(userId);
        if (user == null) {
            LOG.error("User with id {} not found", userId);
            throw new EntityNotFoundException(String.format("User with id %d not found", userId));
        }
        user.setEnabled(false);
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(), "Admin suspended your profile in Tusk Manager");
        LOG.debug("Admin suspend user with id {}", userId);
    }

    @Override
    public void unSuspendUser(Integer userId) {
        User user = userRepository.getOne(userId);
        if (user == null) {
            LOG.error("User with id {} not found", userId);
            throw new EntityNotFoundException(String.format("User %d not found", userId));
        }
        user.setEnabled(true);
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(), "Admin unsuspended your profile in Tusk Manager");
        LOG.debug("Admin unsuspend user with id {}", userId);
    }

    @Override
    public User getUserWithTasks(Integer userId) {
        User user = userRepository.getOneWithTasks(userId);
        if (user == null) {
            LOG.error("User with id {} not found", userId);
            throw new EntityNotFoundException(String.format("User with id %d not found", userId));
        }
        LOG.debug("Admin get user with {} with tasks", userId);
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> userList = userRepository.findAll();
        LOG.debug("Find all users for Admin");
        return userList;
    }

    @Override
    public void createTask(String email, Integer userId, Task task) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            LOG.error("Invalid admin email {}", email);
            throw new ValidationException(String.format("Invalid admin email %s", email));
        }
        User user = userRepository.getOne(userId);
        if (user == null) {
            LOG.error("User with id {} not found", userId);
            throw new EntityNotFoundException(String.format("User with id %d not found", userId));
        }
        task.setNewShared(true);
        task.setCreatedByUserWithEmail(email);
        task.setLastUpdateDescription(String.format("Created by admin with email %s", email));
        task.setLastUpdateDate(LocalDate.now());
        taskRepository.save(task);
        user.getTasks().add(task);
        userRepository.save(user);
        LOG.debug("Admin with email {} create task for user with {}", email, userId);
    }

    @Override
    public Task updateTask(Integer taskId, String email, Integer userId, Task updated) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            LOG.error("Invalid admin email {}", email);
            throw new ValidationException(String.format("Invalid admin email %s", email));
        }
        User user = userRepository.getOne(userId);
        Task task = taskRepository.getOne(taskId);
        if (user == null) {
            LOG.error("User with id {} not found", userId);
            throw new EntityNotFoundException(String.format("User with id %d not found", userId));
        }
        if (task == null) {
            LOG.error("Task with id {} not found", taskId);
            throw new EntityNotFoundException(String.format("Task with id %d not found", taskId));
        }
        if (!user.getTasks().contains(task)) {
            LOG.error("User with id {} does not contain a task with id {}", userId, taskId);
            throw new AccessDeniedException(String.format(
                    "User with id %d does not contain a task with id %d", userId, taskId));
        }
        task.setDescription(updated.getDescription());
        task.setEstimatedDays(updated.getEstimatedDays());
        task.setNewShared(updated.getNewShared());
        task.setCompleted(updated.getCompleted());
        task.setLastUpdateDescription(String.format("Updated by admin with email %s", email));
        task.setLastUpdateDate(LocalDate.now());
        emailService.sendEmail(user.getEmail(), "Admin updated your task in Tusk Manager");
        LOG.debug("Admin with email {} update task with id {} for user with id {}",
                email, taskId, userId);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAllTasks() {
        List<Task> taskList = taskRepository.findAll();
        LOG.debug("Find all tasks for admin");
        return taskList;
    }

}
