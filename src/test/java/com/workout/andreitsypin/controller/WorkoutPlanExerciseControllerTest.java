package com.workout.andreitsypin.controller;

import com.workout.andreitsypin.model.WorkoutPlanExercise;
import com.workout.andreitsypin.service.WorkoutPlanExerciseService;
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
class WorkoutPlanExerciseControllerTest {

    @Mock
    private WorkoutPlanExerciseService service;

    @InjectMocks
    private WorkoutPlanExerciseController controller;

    @Test
    void getAll_shouldReturnAllAssociations() {
        // Arrange
        WorkoutPlanExercise wpe1 = new WorkoutPlanExercise();
        WorkoutPlanExercise wpe2 = new WorkoutPlanExercise();
        when(service.findAll()).thenReturn(Arrays.asList(wpe1, wpe2));

        // Act
        List<WorkoutPlanExercise> result = controller.getAll();

        // Assert
        assertEquals(2, result.size());
        verify(service, times(1)).findAll();
    }

    @Test
    void getById_shouldReturnAssociation_whenExists() {
        // Arrange
        WorkoutPlanExercise wpe = new WorkoutPlanExercise();
        wpe.setId(1L);
        when(service.findById(1L)).thenReturn(Optional.of(wpe));

        // Act
        ResponseEntity<WorkoutPlanExercise> response = controller.getById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void create_shouldSaveAndReturnAssociation() {
        // Arrange
        WorkoutPlanExercise wpe = new WorkoutPlanExercise();
        when(service.save(wpe)).thenReturn(wpe);

        // Act
        WorkoutPlanExercise result = controller.create(wpe);

        // Assert
        assertNotNull(result);
        verify(service, times(1)).save(wpe);
    }

    @Test
    void update_shouldUpdateAndReturnAssociation_whenExists() {
        // Arrange
        WorkoutPlanExercise existing = new WorkoutPlanExercise();
        existing.setId(1L);
        WorkoutPlanExercise updated = new WorkoutPlanExercise();
        updated.setId(1L);

        when(service.findById(1L)).thenReturn(Optional.of(existing));
        when(service.save(updated)).thenReturn(updated);

        // Act
        ResponseEntity<WorkoutPlanExercise> response = controller.update(1L, updated);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void delete_shouldReturnNoContent_whenExists() {
        // Arrange
        when(service.findById(1L)).thenReturn(Optional.of(new WorkoutPlanExercise()));

        // Act
        ResponseEntity<Void> response = controller.delete(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(1L);
    }
}