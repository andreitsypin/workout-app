package com.example.workout_tracker_app.service;

import com.example.workout_tracker_app.model.Exercise;
import com.example.workout_tracker_app.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public Exercise findById(Long id) {
        return exerciseRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        exerciseRepository.deleteById(id);
    }
}
