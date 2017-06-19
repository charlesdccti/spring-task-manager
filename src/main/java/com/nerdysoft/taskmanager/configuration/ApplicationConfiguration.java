package com.nerdysoft.taskmanager.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@ComponentScan
@PropertySource(value = {"classpath:email.properties"})
@Import({ServiceConfiguration.class, MvcConfiguration.class, JpaMysqlConfiguration.class, JpaHsqldbConfiguration.class})
public class ApplicationConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JavaMailSenderImpl mailSender(@Value("${mail.host}") String mailHost,
                                         @Value("${mail.port}") Integer mailPort,
                                         @Value("${mail.username}") String mailUsername,
                                         @Value("${mail.password}") String mailPassword,
                                         @Value("${mail.smtp.auth}") String mailSmtpAuth,
                                         @Value("${mail.smtp.port}") String mailSmtpPort,
                                         @Value("${mail.smtp.socketFactory.class}")
                                                 String mailSmtpSocketFactoryClass,
                                         @Value("${mail.smtp.socketFactory.port}")
                                                 String mailSmtpSocketFactoryPort) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        Properties props = new Properties();
        props.put("mail.smtp.auth", mailSmtpAuth);
        props.put("mail.smtp.port", mailSmtpPort);
        props.put("mail.smtp.socketFactory.class", mailSmtpSocketFactoryClass);
        props.put("mail.smtp.socketFactory.port", mailSmtpSocketFactoryPort);
        javaMailSender.setJavaMailProperties(props);
        javaMailSender.setHost(mailHost);
        javaMailSender.setPort(mailPort);
        javaMailSender.setUsername(mailUsername);
        javaMailSender.setPassword(mailPassword);
        return javaMailSender;
    }

}
