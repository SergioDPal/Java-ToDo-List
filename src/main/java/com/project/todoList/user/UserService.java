package com.project.todoList.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserRepository getUserRepository() {
    return userRepository;
  }

  public String getUser(User user) {
    Optional<User> userFromEmail = userRepository.findUserByEmail(user.getEmail());
    System.out.println(user);
    if (userFromEmail.isPresent()) {
      return userFromEmail.toString();
    }
    return "User not found";
  }

  public static User getUser(Long userId,UserRepository userRepository) {
    Optional<User> user = userRepository.findUserById(userId);
    return user.get();
  }

  public String createUser(User user) {
    Optional<User> userEmail = userRepository.findUserByEmail(user.getEmail());
    System.out.println(user);
    if (userEmail.isPresent()) {
      return "User already exists";
    }
    userRepository.save(user);
    return user.toString();
  }

  public String deleteUser(Long userId) {
    Optional<User> user = userRepository.findUserById(userId);
    if (user.isPresent()) {
      userRepository.delete(user.get());
      return "User deleted";
    }
    return "User not found";
  }

  public String updateUser(Long userId, String username, String email, String password) {
    Optional<User> user = userRepository.findUserById(userId);
    if (user.isPresent()) {
      if (username != null && !username.isEmpty()) {
        user.get().setUsername(username);
      }
      if (email != null && !email.isEmpty()) {
        user.get().setEmail(email);
      }
      if (password != null && !password.isEmpty()) {
        user.get().setPassword(password);
      }
      System.out.println(user.get());
      userRepository.save(user.get());
      return "User updated";
    }
    return "User not found";
  }
  
}
