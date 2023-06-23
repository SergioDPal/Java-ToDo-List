package com.project.todoList.user;

public class UserDTO {

  private String token;
  private String username;
  private String email;
  private String message;
  private Long id;

  public UserDTO() {
  }

  public UserDTO(String token, String username, String email, Long id) {
    this.token = token;
    this.username = username;
    this.email = email;
    this.id = id;
  }

  public UserDTO(String token, String username, String email, String message, Long id) {
    this.token = token;
    this.username = username;
    this.email = email;
    this.message = message;
    this.id = id;
  }
  
  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }

  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  public String getMessage() {
    return message;
  }

}
