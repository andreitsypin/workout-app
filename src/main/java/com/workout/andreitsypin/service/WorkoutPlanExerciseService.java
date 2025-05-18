package com.workout.andreitsypin.service;

import com.workout.andreitsypin.model.WorkoutPlanExercise;
import com.workout.andreitsypin.repository.WorkoutPlanExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutPlanExerciseService {
    private final WorkoutPlanExerciseRepository repository;

    public WorkoutPlanExerciseService(WorkoutPlanExerciseRepository repository) {
        this.repository = repository;
    }

    public List<WorkoutPlanExercise> findAll() {
        return repository.findAll();
    }

    public Optional<WorkoutPlanExercise> findById(Long id) {
        return repository.findById(id);
    }

    public WorkoutPlanExercise save(WorkoutPlanExercise wpe) {
        return repository.save(wpe);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
