package com.project.todoList.user;

import java.util.Map;
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


@RestController
@RequestMapping(path = "/user")
public class UserController {
  
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public String getUser(@RequestBody User user) {
    System.out.println(user);
    return userService.getUser(user).toString();
  }

  @PostMapping
  public String createUser(@RequestBody User user) {
    return userService.createUser(user);
  }

  @PostMapping(path = "/login")
  public String loginUser(@RequestBody Map<String, String> user) {
    String email = user.get("email");
    String password = user.get("password");
    return userService.loginUser(email, password);
  }

  @DeleteMapping(path="{userId}") 
  public String deleteUser(
      @PathVariable("userId") Long userId, 
      @RequestHeader("Authorization") String token){  
    if (!isAuthorized(token, userId)) {
      return "Unauthorized";
    }
    return userService.deleteUser(userId);
  }
  
  @PutMapping(path="{userId}")
  public String updateUser(
      @PathVariable("userId") Long userId,
      @RequestBody Map<String, String> userData, 
      @RequestHeader("Authorization") String token){
    if (!isAuthorized(token, userId)) {
      return "Unauthorized";
    }
    return userService.updateUser(userData, userId);
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

