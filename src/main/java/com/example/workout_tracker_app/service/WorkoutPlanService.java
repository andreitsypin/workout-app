package com.example.workout_tracker_app.service;

import com.example.workout_tracker_app.model.WorkoutPlan;
import com.example.workout_tracker_app.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkoutPlanService {

    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;

    public WorkoutPlan saveWorkoutPlan(WorkoutPlan workoutPlan) {
        return workoutPlanRepository.save(workoutPlan);
    }

    public WorkoutPlan findById(Long id) {
        return workoutPlanRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        workoutPlanRepository.deleteById(id);
    }
}
