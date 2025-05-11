package com.example.workout_tracker_app.service;

import com.example.workout_tracker_app.model.Workout;
import com.example.workout_tracker_app.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    public Workout findById(Long id) {
        return workoutRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        workoutRepository.deleteById(id);
    }
}
