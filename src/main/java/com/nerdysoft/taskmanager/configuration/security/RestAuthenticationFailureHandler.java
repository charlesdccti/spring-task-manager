package com.nerdysoft.taskmanager.configuration.security;

import com.nerdysoft.taskmanager.util.SecurityUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.addHeader("WWW-Authenticate", " Basic realm=\"User Visible Realm\"");
        String message;
        int status;
        if (exception.getLocalizedMessage() == null) {
            message = "Invalid email or password";
            status = HttpServletResponse.SC_UNAUTHORIZED;
        } else {
            message = exception.getLocalizedMessage();
            status = HttpServletResponse.SC_FORBIDDEN;
        }
        SecurityUtil.sendError(request, response, exception, status, message);
    }

}
