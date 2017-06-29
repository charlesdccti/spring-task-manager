package com.nerdysoft.taskmanager.service.impl;

import com.nerdysoft.taskmanager.entity.Task;
import com.nerdysoft.taskmanager.entity.User;
import com.nerdysoft.taskmanager.exception.EntityNotFoundException;
import com.nerdysoft.taskmanager.exception.ValidationException;
import com.nerdysoft.taskmanager.repository.TaskRepository;
import com.nerdysoft.taskmanager.repository.UserRepository;
import com.nerdysoft.taskmanager.service.EmailService;
import com.nerdysoft.taskmanager.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.nerdysoft.taskmanager.entity.User.EMAIL_PATTERN;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger LOG = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Inject
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository,
                           EmailService emailService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public Task getOne(Integer id) {
        Task task = taskRepository.getOne(id);
        LOG.debug("Get task with id {}", task.getId());
        return task;
    }

    @Override
    public List<Task> findByUserId(Integer userId) {
        List<Task> taskList = taskRepository.findByUserId(userId);
        LOG.debug("Find all tasks for user with id {}", userId);
        return taskList;
    }

    @Override
    public List<Task> findAll() {
        List<Task> taskList = taskRepository.findAll();
        LOG.debug("Find all tasks");
        return taskList;
    }

    @Override
    public Task save(Integer userId, Task task) {
        User user = userRepository.getOne(userId);
        task.setCreatedBy(user.getEmail());
        task.setLastUpdateDate(LocalDate.now());
        task.getUsers().add(user);
        Task savedTask = taskRepository.save(task);
        savedTask.setUsers(Collections.emptyList());
        LOG.debug("User with id {} create new task", userId);
        return savedTask;
    }

    @Override
    public Task update(Integer id, Task updated) {
        Task task = taskRepository.getOne(id);
        task.setDescription(updated.getDescription());
        task.setEstimatedDays(updated.getEstimatedDays());
        task.setCompleted(updated.getCompleted());
        task.setLastUpdateDate(LocalDate.now());
        LOG.debug("Update task with id {}", id);
        return taskRepository.save(task);
    }

    @Override
    public void share(Integer id, String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException(
                    String.format("Could not share task, invalid email %s", email));
        }
        Task task = taskRepository.getOne(id);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException(
                    String.format("Could not share task, user with email %s not found", email));
        }
        task.setLastUpdateDate(LocalDate.now());
        task.getUsers().add(user);
        taskRepository.save(task);
        emailService.sendEmail(user.getEmail(), "You have a new common task in Task Manager");
        LOG.debug("Share task with id {}, for user with email {}", id, email);
    }

    @Override
    public void delete(Integer id) {
        Task task = taskRepository.getOne(id);
        task.setUsers(Collections.emptyList());
        taskRepository.save(task);
        taskRepository.delete(task);
        LOG.debug("Delete task with id {}", id);
    }

    @Override
    public void deleteAllForUser(Integer userId) {
        User user = userRepository.getOne(userId);
        user.setTasks(Collections.emptyList());
        userRepository.save(user);
        LOG.debug("Delete all tasks for user with id {}", userId);
    }
}
