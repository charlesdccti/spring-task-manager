package com.nerdysoft.taskmanager.controller;

import com.nerdysoft.taskmanager.entity.User;
import com.nerdysoft.taskmanager.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Integer id, @Valid @RequestBody User user) {
        return userService.update(id, user);
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable Integer id) {
        return userService.getOne(id);
    }

    @DeleteMapping("/{id}/{confirmedPassword}")
    public void delete(@PathVariable Integer id, @PathVariable String confirmedPassword) {
        userService.delete(id, confirmedPassword);
    }

    @PutMapping("/reset-password-for/{id}/{confirmedPassword}")
    public void resetPassword(@PathVariable Integer id, @PathVariable String confirmedPassword
            , @Valid @RequestBody User user) {
        userService.resetPassword(id, confirmedPassword, user);
    }

    @GetMapping("/restore-password/{email:.+}")
    public void restorePassword(@PathVariable String email) {
        userService.restorePassword(email);
    }

}
