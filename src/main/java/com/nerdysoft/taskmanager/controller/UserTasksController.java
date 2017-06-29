package com.nerdysoft.taskmanager.controller;

import com.nerdysoft.taskmanager.entity.Task;
import com.nerdysoft.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserTasksController {

    private final TaskService taskService;

    @Inject
    public UserTasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/{id}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getTasks(@PathVariable Integer id) {
        return taskService.findByUserId(id);
    }

    @DeleteMapping("/{id}/tasks")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTasks(@PathVariable Integer id) {
        taskService.deleteAllForUser(id);
    }

}
