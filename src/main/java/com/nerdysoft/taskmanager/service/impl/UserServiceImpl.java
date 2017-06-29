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
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public User getOne(Integer id) {
        User user = userRepository.getOne(id);
        LOG.debug("Get user with id {}", user.getId());
        return user;
    }

    @Override
    public List<User> findByTaskId(Integer taskId) {
        List<User> userList = userRepository.findByTaskId(taskId);
        LOG.debug("Find users who have a task with id {}", taskId);
        return userList;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = userRepository.findAll();
        LOG.debug("Find all users");
        return userList;
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
    public User update(Integer id, User updated, Authentication authentication) {
        User user = userRepository.getOne(id);
        user.setUserName(updated.getUserName());
        user.setUserLastName(updated.getUserLastName());
        user.setEmail(updated.getEmail().toLowerCase());
        List<String> authenticationRoles = authentication.getAuthorities()
                .stream().map(authorities -> authorities.getAuthority()).collect(Collectors.toList());
        if (authenticationRoles.contains("ROLE_ADMIN")) {
            user.setEnabled(updated.getEnabled());
            user.setAdmin(updated.getAdmin());
            if (updated.getAdmin() && !user.getRoles().contains(Role.ROLE_ADMIN)) {
                user.getRoles().add(Role.ROLE_ADMIN);
            } else {
                user.setRoles(Collections.singleton(Role.ROLE_USER));
            }
        }
        LOG.debug("Update user with id {}", id);
        return userRepository.save(user);
    }

    @Override
    public void updatePassword(Integer id, String currentPassword, String newPassword) {
        if (!PASSWORD_PATTERN.matcher(currentPassword).matches() ||
                !PASSWORD_PATTERN.matcher(newPassword).matches()) {
            throw new ValidationException(
                    String.format("Could not update password for user with id %d , invalid password", id));
        }
        User user = userRepository.getOne(id);
        if (!bCryptPasswordEncoder.matches(currentPassword, user.getPassword())) {
            throw new AccessDeniedException(
                    String.format("Could not update password, user with id %d has not confirmed password", id));
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(), "Your password is updated in Task Manager");
        LOG.debug("User with id {} update password", id);
    }

    @Override
    public void resetPassword(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException(
                    String.format("Could not reset password, invalid email %s", email));
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException(
                    String.format("Could not reset password, user with email %s not found", email));
        }
        String newPassword = UUID.randomUUID().toString().replace("-", "P$");
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        emailService.sendEmail(email, String.format(
                "Password reset in Task Manager, your new password: %s", newPassword));
        LOG.debug("User with email {} reset password", email);
    }

    @Override
    public void delete(Integer id, String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new ValidationException(
                    String.format("Could not delete user with id %d, invalid password", id));
        }
        User user = userRepository.getOne(id);
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new AccessDeniedException(
                    String.format("Could not delete user with id %d, incorrect password", id));
        }
        user.setTasks(Collections.emptyList());
        userRepository.save(user);
        userRepository.delete(user);
        emailService.sendEmail(user.getEmail(), "Your account has been deleted in Task Manager");
        LOG.debug("Delete user with id {}", id);
    }
}
