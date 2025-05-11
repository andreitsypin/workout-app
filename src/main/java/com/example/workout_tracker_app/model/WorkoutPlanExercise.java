package com.example.workout_tracker_app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(WorkoutPlanExerciseId.class)
public class WorkoutPlanExercise {

    @Id
    private Long workoutPlanId;

    @Id
    private Long exerciseId;

    // Getters and setters
}