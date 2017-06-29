package com.nerdysoft.taskmanager.controller;

import com.nerdysoft.taskmanager.entity.Task;
import com.nerdysoft.taskmanager.entity.User;
import com.nerdysoft.taskmanager.service.TaskService;
import com.nerdysoft.taskmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskUsersController {

    private final TaskService taskService;
    private final UserService userService;

    @Inject
    public TaskUsersController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping(value = "/{id}/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers(@PathVariable Integer id) {
        return userService.findByTaskId(id);
    }

    @PostMapping(value = "/users/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@PathVariable Integer id, @Valid @RequestBody Task task) {
        return taskService.save(id, task);
    }

    @PutMapping("/{id}/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void shareTask(@PathVariable Integer id, @RequestParam(value = "email") String email) {
        taskService.share(id, email);
    }

}
