package com.project.todoList.task;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.todoList.user.User;

public interface TaskRepository extends JpaRepository<Task, Long>{

  List<Task> findAll();

  Optional<Task> findById(Long taskId);

  void delete(Task task);

  List<Task> findByUserId(Long userId);

  List<Task> findByUser(User user);
  
}
