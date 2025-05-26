package com.workout.andreitsypin.controller;

import com.workout.andreitsypin.model.Workout;
import com.workout.andreitsypin.service.WorkoutService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutControllerTest {

    @Mock
    private WorkoutService service;

    @InjectMocks
    private WorkoutController controller;

    @Test
    void getAll_shouldReturnAllWorkouts() {
        // Arrange
        Workout workout1 = new Workout();
        Workout workout2 = new Workout();
        when(service.findAll()).thenReturn(Arrays.asList(workout1, workout2));

        // Act
        List<Workout> result = controller.getAll();

        // Assert
        assertEquals(2, result.size());
        verify(service, times(1)).findAll();
    }

    @Test
    void getById_shouldReturnWorkout_whenExists() {
        // Arrange
        Workout workout = new Workout();
        workout.setId(1L);
        when(service.findById(1L)).thenReturn(Optional.of(workout));

        // Act
        ResponseEntity<Workout> response = controller.getById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void create_shouldSaveAndReturnWorkout() {
        // Arrange
        Workout workout = new Workout();
        when(service.save(workout)).thenReturn(workout);

        // Act
        Workout result = controller.create(workout);

        // Assert
        assertNotNull(result);
        verify(service, times(1)).save(workout);
    }
}