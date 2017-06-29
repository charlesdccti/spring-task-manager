package com.nerdysoft.taskmanager.service;

import com.nerdysoft.taskmanager.entity.Task;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface TaskService {

    @PreAuthorize("hasRole('ROLE_ADMIN') OR" +
            " @permissionEvaluator.hasPermissionForTask(authentication, #id)")
    Task getOne(@P("id") Integer id);

    @PreAuthorize("hasRole('ROLE_ADMIN') OR" +
            " @permissionEvaluator.hasPermissionForUser(authentication, #userId)")
    List<Task> findByUserId(@P("userId") Integer userId);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<Task> findAll();

    @PreAuthorize("@permissionEvaluator.hasPermissionForUser(authentication, #userId)")
    Task save(@P("userId") Integer userId, Task task);

    @PreAuthorize("hasRole('ROLE_ADMIN') OR" +
            " @permissionEvaluator.hasPermissionForTask(authentication, #id)")
    Task update(@P("id") Integer id, Task task);

    @PreAuthorize("hasRole('ROLE_ADMIN') OR" +
            " @permissionEvaluator.hasPermissionForTask(authentication, #id)")
    void share(@P("id") Integer id, String email);

    @PreAuthorize("hasRole('ROLE_ADMIN') OR" +
            " @permissionEvaluator.hasPermissionForTask(authentication, #id)")
    void delete(@P("id") Integer id);

    @PreAuthorize("hasRole('ROLE_ADMIN') OR" +
            " @permissionEvaluator.hasPermissionForUser(authentication, #userId)")
    void deleteAllForUser(@P("userId") Integer userId);

}
