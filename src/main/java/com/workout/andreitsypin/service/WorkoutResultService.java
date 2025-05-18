package com.workout.andreitsypin.service;

import com.workout.andreitsypin.model.WorkoutResult;
import com.workout.andreitsypin.repository.WorkoutResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutResultService {
    private final WorkoutResultRepository repository;

    public WorkoutResultService(WorkoutResultRepository repository) {
        this.repository = repository;
    }

    public List<WorkoutResult> findAll() {
        return repository.findAll();
    }

    public Optional<WorkoutResult> findById(Long id) {
        return repository.findById(id);
    }

    public WorkoutResult save(WorkoutResult result) {
        return repository.save(result);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
