package com.example.workout_tracker_app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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
