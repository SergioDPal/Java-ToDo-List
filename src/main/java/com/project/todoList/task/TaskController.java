package com.project.todoList.task;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JwtTokenUtil;
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
  public String createTask(@RequestBody Task task, @RequestHeader("Authorization") String token) {
    JwtTokenUtil jwt = new JwtTokenUtil(); 
    if (jwt.isTokenExpired(token)) {
      return "Token Expired";
    }
    String username = jwt.getUsernameFromToken(token);
    User user = UserService.getUser(username,userRepository);
    task.setUser(user);

    return taskService.createTask(task);
  }

  @GetMapping
  public List<Task> getTasks() {
    return taskService.getTasks();
  }
  
  @GetMapping(path = "{taskId}")
  public String getTask(@PathVariable Long taskId) {
    return taskService.getTask(taskId).toString();
  }

  @GetMapping(path = "user/{userId}")
  public List<Task> getTasksByUser(@PathVariable Long userId) {
    User user = UserService.getUser(userId,userRepository);
    return taskService.getTasksByUser(user);
  }


  @PutMapping(path = "{taskId}")
  public String updateTask(@RequestBody Task task, @RequestHeader("Authorization") String token, @PathVariable Long taskId) {
    if (!isAuthorized(token, taskId)) {
      return "Unauthorized";
    }
    return taskService.updateTask(task, taskId);
  }

  @DeleteMapping(path = "{taskId}")
  public String deleteTask(@PathVariable Long taskId, @RequestHeader("Authorization") String token) {
    if (!isAuthorized(token, taskId)) {
      return "Unauthorized";
    }
    return taskService.deleteTask(taskId);
  }


  private boolean isAuthorized(String token, Long taskId) {
    JwtTokenUtil jwt = new JwtTokenUtil(); 
    if (jwt.isTokenExpired(token)) {
      return false;
    }
    String taskUsername = taskService.getTask(taskId).getUser().getUsername();
    String TokenUsername = jwt.getUsernameFromToken(token);
    if (!taskUsername.equals(TokenUsername)) {
      return false;
    }
    return true;
  }

}
