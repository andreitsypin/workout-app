package com.workout.andreitsypin.controller;

import com.workout.andreitsypin.model.WorkoutResult;
import com.workout.andreitsypin.service.WorkoutResultService;
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
class WorkoutResultControllerTest {

    @Mock
    private WorkoutResultService service;

    @InjectMocks
    private WorkoutResultController controller;

    @Test
    void getAll_shouldReturnAllResults() {
        // Arrange
        WorkoutResult result1 = new WorkoutResult();
        WorkoutResult result2 = new WorkoutResult();
        when(service.findAll()).thenReturn(Arrays.asList(result1, result2));

        // Act
        List<WorkoutResult> result = controller.getAll();

        // Assert
        assertEquals(2, result.size());
        verify(service, times(1)).findAll();
    }

    @Test
    void create_shouldSaveAndReturnResult() {
        // Arrange
        WorkoutResult result = new WorkoutResult();
        when(service.save(result)).thenReturn(result);

        // Act
        WorkoutResult created = controller.create(result);

        // Assert
        assertNotNull(created);
        verify(service, times(1)).save(result);
    }

    @Test
    void update_shouldUpdateAndReturnResult_whenExists() {
        // Arrange
        WorkoutResult existing = new WorkoutResult();
        existing.setId(1L);
        WorkoutResult updated = new WorkoutResult();
        updated.setId(1L);

        when(service.findById(1L)).thenReturn(Optional.of(existing));
        when(service.save(updated)).thenReturn(updated);

        // Act
        ResponseEntity<WorkoutResult> response = controller.update(1L, updated);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }
}