package com.nerdysoft.taskmanager.util;

import com.nerdysoft.taskmanager.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public final class SecurityTestUtil {

    private SecurityTestUtil() {
    }

    public static RequestPostProcessor userAuth(User user) {
        return SecurityMockMvcRequestPostProcessors.authentication(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    }

}
