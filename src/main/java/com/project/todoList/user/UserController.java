package com.project.todoList.user;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    return userService.getUser(user);
  }

  @PostMapping
  public String createUser(@RequestBody User user) {
    return userService.createUser(user);
  }

  @DeleteMapping(path="{userId}") 
  public String deleteUser(@PathVariable("userId") Long userId){
    return userService.deleteUser(userId);
  }
  
  @PutMapping(path="{userId}")
  public String updateUser(
      @PathVariable("userId") Long userId,
      @RequestBody(required = false) User user){
    return userService.updateUser(userId, user.getUsername(), user.getEmail(), user.getPassword());
  }
  
}

