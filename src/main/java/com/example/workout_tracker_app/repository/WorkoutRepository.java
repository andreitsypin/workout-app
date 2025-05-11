package com.example.workout_tracker_app.repository;

import com.example.workout_tracker_app.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}
