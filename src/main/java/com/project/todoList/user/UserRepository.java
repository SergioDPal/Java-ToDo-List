package com.project.todoList.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long>{
  
  Optional<User> findUserById(long userId);

  Optional<User> findUserByEmail(String email);

  Optional<User> findUserByUsername(String username);
}
