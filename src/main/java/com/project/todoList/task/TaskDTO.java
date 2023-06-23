package com.project.todoList.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDTO {
  private long id;
  private String title;
  private String description;
  private String status;
  private String priority;
  private LocalDate dueDate;
  private long userId;

  public TaskDTO() {
  }

  public TaskDTO(long id, String title, String description, String status, String priority, LocalDate dueDate,
      long userId) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.status = status;
    this.priority = priority;
    this.dueDate = dueDate;
    this.userId = userId;
  }
  
  public TaskDTO(long id, String title, String description, String status, String priority, LocalDate dueDate) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.status = status;
    this.priority = priority;
    this.dueDate = dueDate;
  }

  public TaskDTO(String title, String description, String status, String priority) {
    this.title = title;
    this.description = description;
    this.status = status;
    this.priority = priority;
  }

  public TaskDTO(Task task) {
    this.id = task.getId();
    this.title = task.getTitle();
    this.description = task.getDescription();
    this.status = task.getStatus();
    this.priority = task.getPriority();
    this.dueDate = task.getDueDate();
    this.userId = task.getUser().getId();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public static List<TaskDTO> convert(List<Task> tasks) {
    List<TaskDTO> tasksDTO = new ArrayList<>();
    for (Task task : tasks) {
      TaskDTO taskDTO = new TaskDTO(task);
      tasksDTO.add(taskDTO);
    }
    return tasksDTO;
  }
}
