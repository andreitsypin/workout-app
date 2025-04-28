package com.example.workout_tracker_app.model;

import jakarta.persistence.*;

@Entity
@IdClass(WorkoutPlanExerciseId.class)
public class WorkoutPlanExercise {

    @Id
    private Long workoutPlanId;

    @Id
    private Long exerciseId;

    // Getters and setters
}