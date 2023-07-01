package com.project.todoList.task;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.todoList.user.User;
import com.project.todoList.user.UserService;

@Service
public class TaskService {

  private final TaskRepository taskRepository;


  public TaskService(TaskRepository taskRepository, UserService userService) {
    this.taskRepository = taskRepository;

  }

  public List<TaskDTO> getTasks() {
    List<Task> tasks = taskRepository.findAll();
    return TaskDTO.convert(tasks);
    
	}

  public TaskDTO getTask(Long taskId) {
    Optional<Task> task = taskRepository.findById(taskId);
    if (task.isPresent()) {
      return new TaskDTO(task.get());
    }
    throw new IllegalStateException("Task not found");
  }

  public TaskDTO createTask(TaskDTO taskDTO) {
    Task savedTask = taskRepository.save(Task.convert(taskDTO));
    TaskDTO savedTaskDTO = TaskDTO.convert(savedTask);
    return savedTaskDTO;
  }

  public TaskDTO updateTask(TaskDTO taskDTO, Long taskId) throws Exception {
    Optional<Task> taskToUpdate = taskRepository.findById(taskId);
    try {
      if (taskDTO.getTitle() != null) {
        taskToUpdate.get().setTitle(taskDTO.getTitle());
      }
      if (taskDTO.getDescription() != null) {
        taskToUpdate.get().setDescription(taskDTO.getDescription());
      }
      if (taskDTO.getDueDate() != null) {
        taskToUpdate.get().setDueDate(taskDTO.getDueDate());
      }
      if (taskDTO.getStatus() != null) {
        taskToUpdate.get().setStatus(taskDTO.getStatus());
      }
      taskRepository.save(taskToUpdate.get());

      return new TaskDTO(taskToUpdate.get());
    } catch (Exception e) {
      throw new Exception("Task not found");
    }
  }

  public String deleteTask(Long taskId) {
    Optional<Task> task = taskRepository.findById(taskId);
    if (task.isPresent()) {
      taskRepository.delete(task.get());
      return "Task deleted";
    }
    return "Task not found";
  }

  public List<TaskDTO> getTasksByUser(User user) {
    List<Task> tasks = taskRepository.findByUser(user);
    return TaskDTO.convert(tasks);
  }


  
}
