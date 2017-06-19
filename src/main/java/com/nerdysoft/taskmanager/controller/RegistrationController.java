package com.nerdysoft.taskmanager.controller;

import com.nerdysoft.taskmanager.entity.User;
import com.nerdysoft.taskmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private final UserService userService;

    @Inject
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@Valid @RequestBody User user) {
        userService.save(user);
    }

}
