package com.project.todoList.task;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.todoList.user.User;
import com.project.todoList.user.UserService;

@Service
public class TaskService {

  private final TaskRepository taskRepository;
  private final UserService userService;

  public TaskService(TaskRepository taskRepository, UserService userService) {
    this.taskRepository = taskRepository;
    this.userService = userService;
  }

	public List<Task> getTasks() {
    return taskRepository.findAll();
	}

  public String getTask(Long taskId) {
    Optional<Task> task = taskRepository.findById(taskId);
    if (task.isPresent()) {
      return task.toString();
    }
    return "Task not found";
  }

  public String createTask(Task task, Long userId) {
    User user = userService.getUser(userId);
    task.setUser(user);
    taskRepository.save(task);
    return "Task created";
  }

  public String updateTask(Task task, Long taskId) {
    Optional<Task> taskToUpdate = taskRepository.findById(taskId);
    if (taskToUpdate.isPresent()) {
      if (task.getTitle() != null) {
        taskToUpdate.get().setTitle(task.getTitle());
      }
      if (task.getDescription() != null) {
        taskToUpdate.get().setDescription(task.getDescription());
      }
      if (task.getDueDate() != null) {
        taskToUpdate.get().setDueDate(task.getDueDate());
      }
      if (task.getStatus() != null) {
        taskToUpdate.get().setStatus(task.getStatus());
      }
      taskRepository.save(taskToUpdate.get());
      return "Task updated";
    }
    return "Task not found";
  }

  public String deleteTask(Long taskId) {
    Optional<Task> task = taskRepository.findById(taskId);
    if (task.isPresent()) {
      taskRepository.delete(task.get());
      return "Task deleted";
    }
    return "Task not found";
  }

  public List<Task> getTasksByUser(Long userId) {
    return taskRepository.findByUserId(userId);
  }


  
}
