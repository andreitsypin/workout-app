package com.example.workout_tracker_app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idCreator;
    private String name;
    private String description;

    @ManyToMany
    private List<Exercise> exercises;

    // Getters and setters
}
