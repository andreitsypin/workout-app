package com.example.workout_tracker_app.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String email;
    private String name;
    private String role;
    private LocalDate birthdate;
    private Integer height;
    private Integer weight;

    // Getters and setters
}

