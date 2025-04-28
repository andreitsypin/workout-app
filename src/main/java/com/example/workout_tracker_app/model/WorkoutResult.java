package com.example.workout_tracker_app.model;

import jakarta.persistence.*;

@Entity
public class WorkoutResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long workoutId;
    private Long exerciseId;
    private Integer reps;
    private Double weight;
    private Integer duration;

    // Getters and setters
}