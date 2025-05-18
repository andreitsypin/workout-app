package com.workout.andreitsypin.service;

import com.workout.andreitsypin.model.Exercise;
import com.workout.andreitsypin.repository.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {
    private final ExerciseRepository repository;

    public ExerciseService(ExerciseRepository repository) {
        this.repository = repository;
    }

    public List<Exercise> findAll() {
        return repository.findAll();
    }

    public Optional<Exercise> findById(Long id) {
        return repository.findById(id);
    }

    public Exercise save(Exercise exercise) {
        return repository.save(exercise);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
