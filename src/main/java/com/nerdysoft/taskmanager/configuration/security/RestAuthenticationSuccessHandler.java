package com.nerdysoft.taskmanager.configuration.security;

import com.nerdysoft.taskmanager.util.SecurityUtil;
import com.nerdysoft.taskmanager.entity.User;
import com.nerdysoft.taskmanager.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userService;

    @Inject
    public RestAuthenticationSuccessHandler(UserRepository userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {
        User user = userService.findByEmail(authentication.getName());
        user.setTasks(Collections.emptyList());
        SecurityUtil.sendResponse(request, response, HttpServletResponse.SC_OK, user);
    }

}
