package com.nerdysoft.taskmanager.util;

import com.nerdysoft.taskmanager.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

public final class SecurityTestUtil {

    private SecurityTestUtil() {
    }

    public static final RequestBuilder LOGIN_SUCCESS = formLogin("/api/login")
            .user("email", "eric_cartman@gmail.com").password("g5j$3p4xNxW37GQw");

    public static final RequestBuilder LOGIN_FAILURE = formLogin("/api/login")
            .user("email", "eric__cartman@gmail.com").password("4p$4xNxW37FGw");

    public static final RequestBuilder LOGIN_WITH_SUSPENDED_USER = formLogin("/api/login")
            .user("email", "timmy_timmy@gmail.com").password("g5j$3p4xNxW37GQw");

    public static RequestPostProcessor userAuth(User user) {
        return SecurityMockMvcRequestPostProcessors.authentication(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    }
}
