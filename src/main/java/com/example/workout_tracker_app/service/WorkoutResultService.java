package com.example.workout_tracker_app.service;

import com.example.workout_tracker_app.model.WorkoutResult;
import com.example.workout_tracker_app.repository.WorkoutResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkoutResultService {

    @Autowired
    private WorkoutResultRepository workoutResultRepository;

    public WorkoutResult saveWorkoutResult(WorkoutResult result) {
        return workoutResultRepository.save(result);
    }

    public WorkoutResult findById(Long id) {
        return workoutResultRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        workoutResultRepository.deleteById(id);
    }
}
