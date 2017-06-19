package com.nerdysoft.taskmanager.controller;

import com.nerdysoft.taskmanager.entity.Task;
import com.nerdysoft.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Inject
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/save-for/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@PathVariable Integer userId, @Valid @RequestBody Task task) {
        taskService.save(task, userId);
    }

    @PutMapping("/update/{id}/for/{userId}")
    public Task update(@PathVariable Integer id, @PathVariable Integer userId,
                       @Valid @RequestBody Task task) {
        return taskService.update(id, userId, task);
    }

    @GetMapping("/get-one/{id}/for/{userId}")
    public Task getOne(@PathVariable Integer id, @PathVariable Integer userId) {
        return taskService.getOne(id, userId);
    }

    @GetMapping("/find-all/for/{userId}")
    public List<Task> findTasksByUserId(@PathVariable Integer userId) {
        return taskService.findTasksByUserId(userId);
    }

    @DeleteMapping("/delete/{id}/for/{userId}")
    public void delete(@PathVariable Integer id, @PathVariable Integer userId) {
        taskService.delete(id, userId);
    }

    @PutMapping("/share/{id}/by-user/{userId}/for/{email:.+}")
    public void share(@PathVariable Integer id, @PathVariable Integer userId,
                      @PathVariable String email) {
        taskService.share(id, userId, email);
    }

    @PutMapping("/check-shared/{id}/for/{userId}")
    public void checkShared(@PathVariable Integer id, @PathVariable Integer userId) {
        taskService.checkShared(id, userId);
    }

}
