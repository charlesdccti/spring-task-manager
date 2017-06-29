package com.nerdysoft.taskmanager.configuration.security;

import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestMatcher implements org.springframework.security.web.util.matcher.RequestMatcher {

    private final AndRequestMatcher andRequestMatcher;

    public RequestMatcher(Map<String, String> patterns) {
        List<org.springframework.security.web.util.matcher.RequestMatcher> requestMatcherList =
                patterns.entrySet().stream()
                        .map(entry -> new NegatedRequestMatcher(new AntPathRequestMatcher(entry.getKey(), entry.getValue())))
                        .collect(Collectors.toList());
        andRequestMatcher = new AndRequestMatcher(requestMatcherList);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return andRequestMatcher.matches(request);
    }

}
