package com.project.todoList.user;
import com.JwtTokenUtil;
import java.util.Map;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

  public UserDTO getUserDTO(UserDTO user) throws Exception {
    Optional<User> userFromEmail = userRepository.findUserByEmail(user.getEmail());
    try {
      UserDTO userDTO = new UserDTO(userFromEmail.get().getUsername(), userFromEmail.get().getEmail());
      return userDTO;
    } catch (Exception e) {
      throw new Exception("User not found");
    }
  }

  public UserDTO getUserDTO(String username) throws Exception {
    Optional<User> userFromUsername = userRepository.findUserByUsername(username);
    try {
      UserDTO userDTO = new UserDTO(userFromUsername.get().getUsername(), userFromUsername.get().getEmail());
      return userDTO;
    } catch (Exception e) {
      throw new Exception("User not found");
    }
  }
  
  public UserDTO getUserDTO(Long userId) throws Exception {
    Optional<User> userFromId = userRepository.findUserById(userId);
    try {
      UserDTO userDTO = new UserDTO(userFromId.get().getUsername(), userFromId.get().getEmail());
      return userDTO;
    } catch (Exception e) {
      throw new Exception("User not found");
    }
  }
  
  public static User getUser(Long userId,UserRepository userRepository) {
    Optional<User> userOptional = userRepository.findUserById(userId);
    return userOptional.get();
  }

  public static User getUser(String username, UserRepository userRepository) {
    Optional<User> userOptional = userRepository.findUserByUsername(username);
    return userOptional.get();
  }

  public static User getUser(User user, UserRepository userRepository) {
    Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
    return userOptional.get();
  }

  public String createUser(User user) {
    Optional<User> userEmail = userRepository.findUserByEmail(user.getEmail());
    System.out.println(user);
    if (userEmail.isPresent()) {
      return "User already exists";
    }
    userRepository.save(user);
    return "User created";
  }

  public String deleteUser(Long userId) {
      userRepository.delete(getUser(userId,userRepository));
      return "User deleted";
  }

  public String updateUser(Map<String,String> userData, Long userId) {
    Optional<User> user = userRepository.findUserById(userId);
    if (user.isPresent()) {
      if (userData.get("password") != null) {
        user.get().setPassword(userData.get("password"));
      }
      if (userData.get("email") != null) {
        user.get().setEmail(userData.get("email"));
      }
      if (userData.get("username") != null) {
        user.get().setUsername(userData.get("username"));
      }
      System.out.println(user.get());
      userRepository.save(user.get());
      return "User updated";
    }
    return "User not found";
  }

public UserDTO loginUser(String email, String password) throws AuthenticationException {
  Optional<User> userFromEmail = userRepository.findUserByEmail(email);

  if (userFromEmail.isPresent()) {
    String dbPassword = userFromEmail.get().getPassword();

    if (new BCryptPasswordEncoder().matches(password, dbPassword)) {
      JwtTokenUtil jwt = new JwtTokenUtil();
      String token = jwt.generateToken(userFromEmail.get().getUsername());

      UserDTO userDTO = new UserDTO(token, userFromEmail.get().getUsername(), userFromEmail.get().getEmail(),
          "Login successful", userFromEmail.get().getId());
      return userDTO;
    }
  }

  throw new AuthenticationException("Error logging in");
}
  
}
