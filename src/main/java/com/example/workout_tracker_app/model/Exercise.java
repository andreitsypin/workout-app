package com.example.workout_tracker_app.model;

import jakarta.persistence.*;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idCreator;
    private String name;
    private String description;
    private String muscleGroup;

    // Getters and setters
}
