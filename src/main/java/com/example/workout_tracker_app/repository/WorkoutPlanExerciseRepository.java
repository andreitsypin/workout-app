package com.example.workout_tracker_app.repository;

import com.example.workout_tracker_app.model.WorkoutPlanExercise;
import com.example.workout_tracker_app.model.WorkoutPlanExerciseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutPlanExerciseRepository extends JpaRepository<WorkoutPlanExercise, WorkoutPlanExerciseId> {
}
