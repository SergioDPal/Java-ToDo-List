package com.project.todoList.user;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
public class User {
  
  @Id
  @SequenceGenerator(
    name = "user_sequence",
    sequenceName = "user_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    generator = "user_sequence",
    strategy = GenerationType.SEQUENCE
  )

  private Long id;
  private String username;
  private String email;
  private String password;

  public User() {}

  public User(String userName, String email, String password) {
    if (userName.trim().isEmpty()) {
      this.username = null;
    } else {
      this.username = userName;
    }

    if (email.trim().isEmpty()) {
      this.email = null;
    } else {
      this.email = email;
    }
    
    System.out.printf("AAAAAAAAAAAAAAAAAAAAAA: " + password);
    if (password.trim().isEmpty()) {
      this.password = null;
    } else {
      this.password = new BCryptPasswordEncoder().encode(password);
    }
  }

  public User(Long id, String userName, String email, String password) {
    this(userName, email, password);
    this.id = id;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getUsername() { return username; }
  public void setUsername(String userName) { this.username = userName; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPassword() { return password; }
  public void setPassword(String password) { 
    this.password = new BCryptPasswordEncoder().encode(password);
   }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", name='" + username + '\'' +
      ", email='" + email + '\'' +
      ", password='" + password + '\'' +
      '}';
  }
}

