package com.project.todoList.task;

import java.time.LocalDate;

import com.project.todoList.user.User;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
@Table(name = "task")
public class Task {
  
  @Id
  @SequenceGenerator(
    name = "task_sequence",
    sequenceName = "task_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    generator = "task_sequence",
    strategy = GenerationType.SEQUENCE
  )
  private Long id;
  private String title;
  private String description;
  private String status;
  private String priority;
  private LocalDate dueDate;

  @ManyToOne(optional = false)
  @Nonnull
  private User user;
  

  public Task() {}

  public Task(String title, String description, String status, String priority, LocalDate dueDate, User user) {
    this.title = title;
    this.description = description;
    this.status = status;
    this.priority = priority;
    this.dueDate = dueDate;
    this.user = user;
  }

  public Task(Long id, String title, String description, String status, String priority, LocalDate dueDate, User user) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.status = status;
    this.priority = priority;
    this.dueDate = dueDate;
    this.user = user;
  }

  public Task(String title, String description, String status, String priority, LocalDate dueDate) {
    this.title = title;
    this.description = description;
    this.status = status;
    this.priority = priority;
    this.dueDate = dueDate;
  }
  
  public static Task convert(TaskDTO taskDTO) {
    return new Task(
      taskDTO.getId(),
      taskDTO.getTitle(),
      taskDTO.getDescription(),
      taskDTO.getStatus(),
      taskDTO.getPriority(),
      taskDTO.getDueDate(),
      taskDTO.getUser()
    );
  }
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }

  public String getPriority() { return priority; }  
  public void setPriority(String priority) { this.priority = priority; }

  public LocalDate getDueDate() { return dueDate; }
  public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

  public User getUser() { return user; }
  public void setUser(User user) { this.user = user; }
}
