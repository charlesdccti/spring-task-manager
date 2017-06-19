package com.nerdysoft.taskmanager.controller;

import com.nerdysoft.taskmanager.entity.Task;
import com.nerdysoft.taskmanager.entity.User;
import com.nerdysoft.taskmanager.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Inject
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create-user")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody User user) {
        adminService.createUser(user);
    }

    @PutMapping("/update-user/{userId}")
    public User updateUser(@PathVariable Integer userId, @Valid @RequestBody User user) {
        return adminService.updateUser(userId, user);
    }

    @DeleteMapping("/delete-user/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        adminService.deleteUser(userId);
    }

    @PutMapping("/suspend-user/{userId}")
    public void suspendUser(@PathVariable Integer userId) {
        adminService.suspendUser(userId);
    }

    @PutMapping("/unsuspend-user/{userId}")
    public void unSuspendUser(@PathVariable Integer userId) {
        adminService.unSuspendUser(userId);
    }

    @PostMapping("/create-task-by/{email}/for/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@PathVariable String email, @PathVariable Integer userId,
                           @Valid @RequestBody Task task) {
        adminService.createTask(email, userId, task);
    }

    @PutMapping("/update-task/{taskId}/by/{email}/for/{userId}")
    public Task updateTask(@PathVariable Integer taskId, @PathVariable String email,
                           @PathVariable Integer userId, @Valid @RequestBody Task task) {
        return adminService.updateTask(taskId, email, userId, task);
    }

    @GetMapping("/get-user-with-tasks/{userId}")
    public User getUserWithTasks(@PathVariable Integer userId) {
        return adminService.getUserWithTasks(userId);
    }

    @GetMapping("/find-all-users")
    public List<User> findAllUsers() {
        return adminService.findAllUsers();
    }

    @GetMapping("/find-all-tasks")
    public List<Task> findAllTasks() {
        return adminService.findAllTasks();
    }


}
