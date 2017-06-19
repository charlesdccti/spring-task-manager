package com.nerdysoft.taskmanager.service.impl;

import com.nerdysoft.taskmanager.entity.Role;
import com.nerdysoft.taskmanager.entity.User;
import com.nerdysoft.taskmanager.exception.EntityNotFoundException;
import com.nerdysoft.taskmanager.exception.ValidationException;
import com.nerdysoft.taskmanager.repository.UserRepository;
import com.nerdysoft.taskmanager.service.EmailService;
import com.nerdysoft.taskmanager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static com.nerdysoft.taskmanager.entity.User.EMAIL_PATTERN;
import static com.nerdysoft.taskmanager.entity.User.PASSWORD_PATTERN;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Inject
    public UserServiceImpl(UserRepository userRepository, EmailService emailService,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void save(User user) {
        user.setEmail(user.getEmail().toLowerCase());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setAdmin(false);
        user.setRegistered(LocalDate.now());
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(), "Welcome to Task Manager");
        LOG.debug("Registered new user with email {}", user.getEmail());
    }

    @Override
    public User update(Integer id, User updated) {
        User user = userRepository.getOne(id);
        user.setUserName(updated.getUserName());
        user.setUserLastName(updated.getUserLastName());
        user.setEmail(updated.getEmail().toLowerCase());
        LOG.debug("Update user with id {}", id);
        return userRepository.save(user);
    }

    @Override
    public User getOne(Integer id) {
        User user = userRepository.getOne(id);
        LOG.debug("Get user with id {}", user.getId());
        return user;
    }

    @Override
    public void delete(Integer id, String confirmedPassword) {
        if (!PASSWORD_PATTERN.matcher(confirmedPassword).matches()) {
            LOG.error("Invalid password {}", confirmedPassword);
            throw new ValidationException(String.format("Invalid password %s", confirmedPassword));
        }
        User user = userRepository.getOne(id);
        if (!bCryptPasswordEncoder.matches(confirmedPassword, user.getPassword())) {
            LOG.error("User with id {} has not confirmed password", id);
            throw new AccessDeniedException(
                    String.format("User with id %d has not confirmed password", id));
        }
        user.setTasks(Collections.emptyList());
        userRepository.save(user);
        userRepository.delete(user);
        emailService.sendEmail(user.getEmail(), "Your account has been deleted in Task Manager");
        LOG.debug("Delete user with id {}", id);
    }

    @Override
    public void resetPassword(Integer id, String confirmedPassword, User updated) {
        if (!PASSWORD_PATTERN.matcher(confirmedPassword).matches()) {
            LOG.error("Invalid password {}", confirmedPassword);
            throw new ValidationException(String.format("Invalid password %s", confirmedPassword));
        }
        User user = userRepository.getOne(id);
        if (!bCryptPasswordEncoder.matches(confirmedPassword, user.getPassword())) {
            LOG.error("User with id {} has not confirmed password", id);
            throw new AccessDeniedException(
                    String.format("User with id %d has not confirmed password", id));
        }
        user.setPassword(bCryptPasswordEncoder.encode(updated.getPassword()));
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(), "Your password is updated in Task Manager");
        LOG.debug("User with id {} reset password", id);
    }

    @Override
    public void restorePassword(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            LOG.error("Invalid email {}", email);
            throw new ValidationException(String.format("Invalid email %s", email));
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            LOG.error("User with email {} not found", email);
            throw new EntityNotFoundException(String.format("User with email %s not found", email));
        }
        String newPassword = UUID.randomUUID().toString().replace("-", "P$");
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        emailService.sendEmail(email, String.format(
                "Password recovery in Task Manager, your new password: %s", newPassword));
        LOG.debug("User with email {} restore password", email);
    }

}
