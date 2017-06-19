package com.nerdysoft.taskmanager.service;

import com.nerdysoft.taskmanager.entity.User;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService {

    void save(User user);

    @PreAuthorize("hasRole('ROLE_USER') AND" +
            " @permissionEvaluator.hasPermissionForUserId(authentication, #id)")
    User update(@P("id") Integer id, User user);

    @PreAuthorize("hasRole('ROLE_USER') AND" +
            " @permissionEvaluator.hasPermissionForUserId(authentication, #id)")
    User getOne(@P("id") Integer id);

    @PreAuthorize("hasRole('ROLE_USER') AND" +
            " @permissionEvaluator.hasPermissionForUserId(authentication, #id)")
    void delete(@P("id") Integer id, String confirmedPassword);

    @PreAuthorize("hasRole('ROLE_USER') AND" +
            " @permissionEvaluator.hasPermissionForUserId(authentication, #id)")
    void resetPassword(@P("id") Integer id, String confirmedPassword, User user);

    void restorePassword(String email);
}
