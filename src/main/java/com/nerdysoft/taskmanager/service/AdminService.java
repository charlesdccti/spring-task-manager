package com.nerdysoft.taskmanager.service;

import com.nerdysoft.taskmanager.entity.Task;
import com.nerdysoft.taskmanager.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface AdminService {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void createUser(User user);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    User updateUser(Integer userId, User user);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteUser(Integer userId);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void suspendUser(Integer userId);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void unSuspendUser(Integer userId);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    User getUserWithTasks(Integer id);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<User> findAllUsers();

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void createTask(String email, Integer userId, Task task);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Task updateTask(Integer taskId, String email, Integer userId, Task task);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<Task> findAllTasks();

}
