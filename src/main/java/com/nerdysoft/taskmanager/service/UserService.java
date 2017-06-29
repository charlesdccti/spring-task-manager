package com.nerdysoft.taskmanager.service;

import com.nerdysoft.taskmanager.entity.User;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {

    @PreAuthorize("hasRole('ROLE_ADMIN') OR" +
            " @permissionEvaluator.hasPermissionForUser(authentication, #id)")
    User getOne(@P("id") Integer id);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<User> findByTaskId(Integer taskId);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<User> findAll();

    void save(User user);

    @PreAuthorize("hasRole('ROLE_ADMIN') OR" +
            " @permissionEvaluator.hasPermissionForUser(authentication, #id)")
    User update(@P("id") Integer id, User user);

    @PreAuthorize("hasRole('ROLE_ADMIN') OR" +
            " @permissionEvaluator.hasPermissionForUser(authentication, #id)")
    void updatePassword(@P("id") Integer id, String currentPassword, String newPassword);

    void resetPassword(String email);

    @PreAuthorize("hasRole('ROLE_ADMIN') OR" +
            " @permissionEvaluator.hasPermissionForUser(authentication, #id)")
    void delete(@P("id") Integer id, String password);

}
