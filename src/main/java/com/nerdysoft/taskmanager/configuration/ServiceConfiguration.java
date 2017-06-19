package com.nerdysoft.taskmanager.configuration;

import com.nerdysoft.taskmanager.configuration.security.PermissionEvaluator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.inject.Inject;

@Configuration
@EnableAsync
@ComponentScan("com.nerdysoft.taskmanager.service")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class ServiceConfiguration extends GlobalMethodSecurityConfiguration {

    private final ApplicationContext applicationContext;
    private final PermissionEvaluator permissionEvaluator;

    @Inject
    public ServiceConfiguration(ApplicationContext applicationContext,
                                PermissionEvaluator permissionEvaluator) {
        this.applicationContext = applicationContext;
        this.permissionEvaluator = permissionEvaluator;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(permissionEvaluator);
        handler.setApplicationContext(applicationContext);
        return handler;
    }

}  
