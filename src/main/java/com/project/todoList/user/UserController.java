package com.project.todoList.user;

import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin
@RestController
@RequestMapping(path = "/user")
public class UserController {
  
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String,String>> getUser(@RequestBody User user) {
    User dbUser = userService.getUser(user);
    Map<String, String> response = new HashMap<>();
    response.put("username", dbUser.getUsername());
    response.put("email", dbUser.getEmail());
    return ResponseEntity.ok(response);
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String,String>> createUser(@RequestBody User user) {
    Map<String, String> response = new HashMap<>();
    response.put("message", userService.createUser(user));

    return ResponseEntity.ok(response);
  }

  @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> loginUser(@RequestBody Map<String, String> user) {
    String email = user.get("email");
    String password = user.get("password");

    try {
      UserDTO userDTO = userService.loginUser(email, password);
      return ResponseEntity.ok(userDTO);
    } catch (AuthenticationException e) {
      UserDTO errorDTO = new UserDTO();
      errorDTO.setMessage(e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }
  }

  @DeleteMapping(path="{userId}", produces = MediaType.APPLICATION_JSON_VALUE) 
  public ResponseEntity<Map<String,String>> deleteUser(
    Map<String, String> responseBody,
      @PathVariable("userId") Long userId, 
      @RequestHeader("Authorization") String token){  
    if (!isAuthorized(token, userId)) {
      responseBody.put("message", "Unauthorized");
      return ResponseEntity.status(401).body(responseBody);
    };
    responseBody.put("message", userService.deleteUser(userId));
    return ResponseEntity.ok(responseBody);
  }
  
  @PutMapping(path="{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String,String>> updateUser(
    Map<String, String> responseBody,
      @PathVariable("userId") Long userId,
      @RequestBody Map<String, String> userData, 
      @RequestHeader("Authorization") String token){
    if (!isAuthorized(token, userId)) {
      responseBody.put("message", "Unauthorized");
      return ResponseEntity.status(401).body(responseBody);
    }
    responseBody.put("message", userService.updateUser(userData, userId));
    return ResponseEntity.ok(responseBody);
  }
  
  private boolean isAuthorized(String token, Long userId) {
    JwtTokenUtil jwt = new JwtTokenUtil(); 
    if (jwt.isTokenExpired(token)) {
      return false;
    }
    String tokenUsername = jwt.getUsernameFromToken(token);
    String dbUsername = UserService.getUser(userId, userService.getUserRepository()).getUsername();
    if (!tokenUsername.equals(dbUsername)) {
      return false;
    }
    return true;
  }
}

