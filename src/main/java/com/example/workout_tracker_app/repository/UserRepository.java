package com.example.workout_tracker_app.repository;

import com.example.workout_tracker_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}

