package com.project.todoList.task;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {
  
  private final TaskService taskService;
  
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping
  public String createTask(@RequestBody Task body) {
    Task task = new Task();
    System.out.printf("body: " + body);
    return taskService.createTask(task, Long.valueOf(1));
  }

  @GetMapping
  public List<Task> getTasks() {
    return taskService.getTasks();
  }
  
  @GetMapping(path = "{taskId}")
  public String getTask(@PathVariable Long taskId) {
    return taskService.getTask(taskId);
  }

  // GET /task
 // /user/{userId}/tasks
  @GetMapping(path = "user/{userId}")
  public List<Task> getTasksByUser(@PathVariable Long userId) {
    return taskService.getTasksByUser(userId);
  }


  @PutMapping(path = "{taskId}")
  public String updateTask(@RequestBody Task task , @PathVariable Long taskId) {
    return taskService.updateTask(task, taskId);
  }

  @DeleteMapping(path = "{taskId}")
  public String deleteTask(@PathVariable Long taskId) {
    return taskService.deleteTask(taskId);
  }



}
