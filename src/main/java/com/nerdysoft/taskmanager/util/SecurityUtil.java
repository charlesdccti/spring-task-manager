package com.nerdysoft.taskmanager.util;

import com.nerdysoft.taskmanager.entity.User;
import com.nerdysoft.taskmanager.model.ErrorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public final class SecurityUtil {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityUtil.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    private SecurityUtil() {
    }

    public static void sendError(HttpServletRequest request, HttpServletResponse response,
                                 Exception exception, int status, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        PrintWriter writer = response.getWriter();
        ErrorInfo errorInfo = new ErrorInfo(request.getRequestURL(), exception, message);
        writer.write(mapper.writeValueAsString(errorInfo));
        writer.flush();
        writer.close();
        LOG.debug("Anonymous with ip {}  port {} , tries to login but receives an error {}",
                request.getRemoteAddr(), request.getRemotePort(), message);
    }

    public static void sendResponse(HttpServletRequest request, HttpServletResponse response,
                                    int status, User user) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(mapper.writeValueAsString(user));
        response.setStatus(status);
        writer.flush();
        writer.close();
        LOG.debug("User with id {} login with ip {}  port {}", user.getId(),
                request.getRemoteAddr(), request.getRemotePort());
    }

}
