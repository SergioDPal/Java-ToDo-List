package com.project.todoList.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.JwtTokenUtil;
import com.project.todoList.user.User;
import com.project.todoList.user.UserRepository;
import com.project.todoList.user.UserService;

@CrossOrigin
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
  public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO, @RequestHeader("Authorization") String token) {
    JwtTokenUtil jwt = new JwtTokenUtil(); 
    if (jwt.isTokenExpired(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    String username = jwt.getUsernameFromToken(token);
    User user = UserService.getUser(username,userRepository);
    taskDTO.setUser(user);
    return ResponseEntity.ok(taskService.createTask(taskDTO));
  }

  @GetMapping
  public ResponseEntity<List<TaskDTO>> getTasks() {
    List<TaskDTO> tasks = taskService.getTasks();
    
    return ResponseEntity.ok(tasks);
  }
  
  @GetMapping(path = "{taskId}")
  public ResponseEntity<TaskDTO> getTask(@PathVariable Long taskId) {
    return ResponseEntity.ok(taskService.getTask(taskId));
  }

  @GetMapping(path = "user/{userId}")
  public ResponseEntity<List<TaskDTO>> getTasksByUser(@PathVariable Long userId) {
    System.out.println("userId: " + userId);
    User user = UserService.getUser(userId,userRepository);
    return ResponseEntity.ok(taskService.getTasksByUser(user));
  }


  @PutMapping(path = "{taskId}")
  public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO taskDTO, @RequestHeader("Authorization") String token, @PathVariable Long taskId) {
    if (isAuthorized(token, taskId)) {
      try {
        return ResponseEntity.ok(taskService.updateTask(taskDTO, taskId));
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
  }

  @DeleteMapping(path = "{taskId}")
  public ResponseEntity<Map<String,String>> deleteTask(@PathVariable Long taskId, @RequestHeader("Authorization") String token) {
        Map<String, String> responseBody = new HashMap<>();
    if (!isAuthorized(token, taskId)) {
      responseBody.put("message", "Unauthorized");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
    responseBody.put("message", taskService.deleteTask(taskId));
    return ResponseEntity.ok(responseBody);
  }


  private boolean isAuthorized(String token, Long taskId) {
    JwtTokenUtil jwt = new JwtTokenUtil(); 
    if (jwt.isTokenExpired(token)) {
      return false;
    }
    Long taskUserId = taskService.getTask(taskId).getUserId();
    String taskUsername = UserService.getUser(taskUserId,userRepository).getUsername();
    String TokenUsername = jwt.getUsernameFromToken(token);
    if (!taskUsername.equals(TokenUsername)) {
      return false;
    }
    return true;
  }

}
