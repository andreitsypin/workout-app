package com.example.workout_tracker_app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long coachId;
    private Long planId;
    private LocalDate date;

    // Getters and setters
}
