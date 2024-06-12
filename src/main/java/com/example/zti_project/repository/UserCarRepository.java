package com.example.zti_project.repository;

import com.example.zti_project.model.UserCar;
import com.example.zti_project.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserCarRepository extends JpaRepository<UserCar, Long> {
    Collection<Object> findByUser(Users user);
}
