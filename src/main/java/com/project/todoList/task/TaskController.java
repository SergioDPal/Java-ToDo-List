package com.project.todoList.task;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.todoList.user.User;
import com.project.todoList.user.UserRepository;
import com.project.todoList.user.UserService;

@RestController
@RequestMapping("/task")
public class TaskController {
  
  private final TaskService taskService;
  private final UserRepository userRepository;
  
  public TaskController(TaskService taskService, UserRepository userRepository) {
    this.userRepository = userRepository;
    this.taskService = taskService;
  }

  @PostMapping
  public String createTask(@RequestBody Map<String, Map<String,String>> body) {

    Map<String, String> taskData = body.get("task");
    Task task = new Task(
      taskData.get("title"),
      taskData.get("description"),
      taskData.get("status"),
      taskData.get("priority"),
      taskData.get("dueDate")
    );
    Long userId = Long.valueOf(body.get("user").get("userId"));
    User user = UserService.getUser(userId,userRepository);
    task.setUser(user);

    return taskService.createTask(task);
  }

  @GetMapping
  public List<Task> getTasks() {
    return taskService.getTasks();
  }
  
  @GetMapping(path = "{taskId}")
  public String getTask(@PathVariable Long taskId) {
    return taskService.getTask(taskId);
  }

  @GetMapping(path = "user/{userId}")
  public List<Task> getTasksByUser(@PathVariable Long userId) {
    User user = UserService.getUser(userId,userRepository);
    return taskService.getTasksByUser(user);
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
