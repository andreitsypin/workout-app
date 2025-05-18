package com.workout.andreitsypin.service;

import com.workout.andreitsypin.model.Workout;
import com.workout.andreitsypin.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {
    private final WorkoutRepository repository;

    public WorkoutService(WorkoutRepository repository) {
        this.repository = repository;
    }

    public List<Workout> findAll() {
        return repository.findAll();
    }

    public Optional<Workout> findById(Long id) {
        return repository.findById(id);
    }

    public Workout save(Workout workout) {
        return repository.save(workout);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
