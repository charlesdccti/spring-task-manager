package com.nerdysoft.taskmanager.handler;

import com.nerdysoft.taskmanager.model.ErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public abstract class AbstractExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractExceptionHandler.class);

    protected ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e) {
        LOG.error("Exception at request " + req.getRequestURL(), e);
        return new ErrorInfo(req.getRequestURL(), e);
    }

    protected ErrorInfo logAndGetValidationErrorInfo(HttpServletRequest req, BindingResult result) {
        String[] details = result.getFieldErrors().stream()
                .map(fe -> fe.getField() + ' ' + fe.getDefaultMessage()).toArray(String[]::new);
        LOG.error("Validation exception at request " + req.getRequestURL() + ": " + Arrays.toString(details));
        return new ErrorInfo(req.getRequestURL(), "ValidationException", details);
    }

}
