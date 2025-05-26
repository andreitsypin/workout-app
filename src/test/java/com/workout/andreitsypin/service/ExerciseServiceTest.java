package com.workout.andreitsypin.service;

import com.workout.andreitsypin.model.Exercise;
import com.workout.andreitsypin.repository.ExerciseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {

    @Mock
    private ExerciseRepository repository;

    @InjectMocks
    private ExerciseService service;

    @Test
    void findAll_shouldReturnAllExercises() {
        // Arrange
        Exercise exercise1 = new Exercise();
        Exercise exercise2 = new Exercise();
        when(repository.findAll()).thenReturn(Arrays.asList(exercise1, exercise2));

        // Act
        List<Exercise> result = service.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnExercise_whenExists() {
        // Arrange
        Exercise exercise = new Exercise();
        exercise.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(exercise));

        // Act
        Optional<Exercise> result = service.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Exercise> result = service.findById(1L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void save_shouldReturnSavedExercise() {
        // Arrange
        Exercise exercise = new Exercise();
        when(repository.save(exercise)).thenReturn(exercise);

        // Act
        Exercise result = service.save(exercise);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).save(exercise);
    }

    @Test
    void delete_shouldCallRepository() {
        // Act
        service.delete(1L);

        // Assert
        verify(repository, times(1)).deleteById(1L);
    }
}