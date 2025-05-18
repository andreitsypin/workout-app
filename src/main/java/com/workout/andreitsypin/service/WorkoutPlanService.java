package com.workout.andreitsypin.service;

import com.workout.andreitsypin.model.WorkoutPlan;
import com.workout.andreitsypin.repository.WorkoutPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutPlanService {
    private final WorkoutPlanRepository repository;

    public WorkoutPlanService(WorkoutPlanRepository repository) {
        this.repository = repository;
    }

    public List<WorkoutPlan> findAll() {
        return repository.findAll();
    }

    public Optional<WorkoutPlan> findById(Long id) {
        return repository.findById(id);
    }

    public WorkoutPlan save(WorkoutPlan plan) {
        return repository.save(plan);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
