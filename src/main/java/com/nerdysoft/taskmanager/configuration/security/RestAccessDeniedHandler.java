package com.nerdysoft.taskmanager.configuration.security;

import com.nerdysoft.taskmanager.util.SecurityUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException exception) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() == null) {
            response.addHeader("WWW-Authenticate", " Basic realm=\"User Visible Realm\"");
            SecurityUtil.sendError(request, response, new AuthenticationServiceException(""),
                    HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
        } else {
            SecurityUtil.sendError(request, response, exception, HttpServletResponse.SC_FORBIDDEN,
                    exception.getLocalizedMessage());
        }
    }

}