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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
    public void save(Task task, Integer userId) {
        User user = userRepository.getOne(userId);
        task.setCreatedBy(user.getEmail());
        task.setNewShared(false);
        task.setLastUpdateDate(LocalDate.now());
        taskRepository.save(task);
        user.getTasks().add(task);
        userRepository.save(user);
        LOG.debug("User with id {} save new task", userId);
    }

    @Override
    public Task update(Integer taskId, Integer userId, Task updated) {
        User user = userRepository.getOneWithTasks(userId);
        Task task = taskRepository.getOne(taskId);
        task.setDescription(updated.getDescription());
        task.setEstimatedDays(updated.getEstimatedDays());
        task.setCompleted(updated.getCompleted());
        task.setLastUpdateDate(LocalDate.now());
        LOG.debug("User with id {} update task with id {}", userId, taskId);
        return taskRepository.save(task);
    }

    @Override
    public Task getOne(Integer id, Integer userId) {
        Task task = taskRepository.getOne(id);
        LOG.debug("User with id {} get task with id {}", userId, task.getId());
        return task;
    }

    @Override
    public List<Task> findTasksByUserId(Integer userId) {
        List<Task> taskList = taskRepository.findTasksByUserId(userId);
        LOG.debug("Find all tasks for user with id {}", userId);
        return taskList;
    }

    @Override
    public void delete(Integer id, Integer userId) {
        User user = userRepository.getOneWithTasks(userId);
        Task task = taskRepository.getOne(id);
        user.getTasks().remove(task);
        userRepository.save(user);
        taskRepository.delete(task);
        LOG.debug("User with id {} delete task with id {}", userId, id);
    }

    @Override
    public void share(Integer id, Integer userId, String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            LOG.error("Invalid email {}", email);
            throw new ValidationException(String.format("Invalid email %s", email));
        }
        Task task = taskRepository.getOne(id);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            LOG.error("User with email {} not found", email);
            throw new EntityNotFoundException(String.format("User with email %s not found", email));
        }
        task.setNewShared(true);
        task.setLastUpdateDate(LocalDate.now());
        taskRepository.save(task);
        user.getTasks().add(task);
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(), "You have a new common task in Task Manager");
        LOG.debug("User with id {} share out task with id {} for user with id {}",
                userId, id, user.getId());
    }

    @Override
    public void checkShared(Integer id, Integer userId) {
        Task task = taskRepository.getOne(id);
        User user = userRepository.getOneWithTasks(userId);
        if (Objects.equals(user.getEmail(), task.getCreatedBy())) {
            LOG.error("For user with id {} access denied", userId);
            throw new AccessDeniedException(String.format("For user with id %d access is denied", userId));
        }
        task.setNewShared(false);
        taskRepository.save(task);
        LOG.debug("User with id {} check a new common task with id {}", userId, id);
    }

}
