package com.nerdysoft.taskmanager.configuration.security;

import com.nerdysoft.taskmanager.entity.User;
import com.nerdysoft.taskmanager.exception.EntityNotFoundException;
import com.nerdysoft.taskmanager.repository.TaskRepository;
import com.nerdysoft.taskmanager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Objects;

@Component
public class PermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {

    private static final Logger LOG = LoggerFactory.getLogger(PermissionEvaluator.class);

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Inject
    public PermissionEvaluator(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }

    public boolean hasPermissionForUser(Authentication authentication, Integer userId) {
        if (!userRepository.exists(userId)) {
            throw new EntityNotFoundException(
                    String.format("User with id %d not found", userId));
        }
        User authenticationUser = userRepository.findByEmail(authentication.getName());
        if (Objects.equals(authenticationUser.getId(), userId)) {
            return true;
        }
        LOG.error("User with id {} does not have permission", userId);
        return false;
    }

    public boolean hasPermissionForTask(Authentication authentication, Integer taskId) {
        if (!taskRepository.exists(taskId)) {
            throw new EntityNotFoundException(
                    String.format("Task with id %d not found", taskId));
        }
        User authenticationUser = userRepository.findByEmail(authentication.getName());
        if (taskRepository.isTaskContainingInUser(taskId, authenticationUser.getId())) {
            return true;
        }
        LOG.error("User with id {} does not have permission", authenticationUser.getId());
        return false;
    }

}
