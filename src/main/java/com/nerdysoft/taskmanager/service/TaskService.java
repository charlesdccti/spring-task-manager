package com.nerdysoft.taskmanager.service;

import com.nerdysoft.taskmanager.entity.Task;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface TaskService {

    @PreAuthorize("hasRole('ROLE_USER') AND" +
            " @permissionEvaluator.hasPermissionForUserId(authentication, #userId)")
    void save(Task task, @P("userId") Integer userId);

    @PreAuthorize("hasRole('ROLE_USER') AND hasPermission(#id, #userId)")
    Task update(@P("id") Integer id, @P("userId") Integer userId, Task task);

    @PreAuthorize("hasRole('ROLE_USER') AND hasPermission(#id, #userId)")
    Task getOne(@P("id") Integer id, @P("userId") Integer userId);

    @PreAuthorize("hasRole('ROLE_USER') AND " +
            "@permissionEvaluator.hasPermissionForUserId(authentication, #id)")
    List<Task> findTasksByUserId(@P("id") Integer id);

    @PreAuthorize("hasRole('ROLE_USER') AND hasPermission(#id, #userId)")
    void delete(@P("id") Integer id, @P("userId") Integer userId);

    @PreAuthorize("hasRole('ROLE_USER') AND hasPermission(#id, #userId)")
    void share(@P("id") Integer id, @P("userId") Integer userId, String email);

    @PreAuthorize("hasRole('ROLE_USER') AND hasPermission(#id, #userId)")
    void checkShared(@P("id") Integer id, @P("userId") Integer userId);

}
