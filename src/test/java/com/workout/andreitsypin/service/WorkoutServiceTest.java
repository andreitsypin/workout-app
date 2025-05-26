package com.workout.andreitsypin.service;

import com.workout.andreitsypin.model.Workout;
import com.workout.andreitsypin.repository.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @Mock
    private WorkoutRepository repository;

    @InjectMocks
    private WorkoutService service;

    @Test
    void findById_shouldReturnWorkout_whenExists() {
        // Arrange
        Workout workout = new Workout();
        workout.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(workout));

        // Act
        Optional<Workout> result = service.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void save_shouldReturnSavedWorkout() {
        // Arrange
        Workout workout = new Workout();
        when(repository.save(workout)).thenReturn(workout);

        // Act
        Workout result = service.save(workout);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).save(workout);
    }
}