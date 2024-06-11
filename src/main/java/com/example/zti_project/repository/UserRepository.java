package com.example.zti_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.zti_project.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
  Optional<Users> findByUsername(String username);

  Boolean existsByUsername(String username);
}
