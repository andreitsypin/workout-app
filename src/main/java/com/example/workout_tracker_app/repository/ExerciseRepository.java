package com.example.workout_tracker_app.repository;

import com.example.workout_tracker_app.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

}
