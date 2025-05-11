package com.example.workout_tracker_app.repository;

import com.example.workout_tracker_app.model.WorkoutResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutResultRepository extends JpaRepository<WorkoutResult, Long> {
}
