package com.workout.andreitsypin.controller;

import com.workout.andreitsypin.model.WorkoutPlan;
import com.workout.andreitsypin.service.WorkoutPlanService;
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
class WorkoutPlanControllerTest {

    @Mock
    private WorkoutPlanService service;

    @InjectMocks
    private WorkoutPlanController controller;

    @Test
    void getAll_shouldReturnAllPlans() {
        // Arrange
        WorkoutPlan plan1 = new WorkoutPlan();
        WorkoutPlan plan2 = new WorkoutPlan();
        when(service.findAll()).thenReturn(Arrays.asList(plan1, plan2));

        // Act
        List<WorkoutPlan> result = controller.getAll();

        // Assert
        assertEquals(2, result.size());
        verify(service, times(1)).findAll();
    }

    @Test
    void getById_shouldReturnPlan_whenExists() {
        // Arrange
        WorkoutPlan plan = new WorkoutPlan();
        plan.setId(1L);
        when(service.findById(1L)).thenReturn(Optional.of(plan));

        // Act
        ResponseEntity<WorkoutPlan> response = controller.getById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void getById_shouldReturnNotFound_whenNotExists() {
        // Arrange
        when(service.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<WorkoutPlan> response = controller.getById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void create_shouldSaveAndReturnPlan() {
        // Arrange
        WorkoutPlan plan = new WorkoutPlan();
        when(service.save(plan)).thenReturn(plan);

        // Act
        WorkoutPlan result = controller.create(plan);

        // Assert
        assertNotNull(result);
        verify(service, times(1)).save(plan);
    }

    @Test
    void update_shouldUpdateAndReturnPlan_whenExists() {
        // Arrange
        WorkoutPlan existing = new WorkoutPlan();
        existing.setId(1L);
        WorkoutPlan updated = new WorkoutPlan();
        updated.setId(1L);

        when(service.findById(1L)).thenReturn(Optional.of(existing));
        when(service.save(updated)).thenReturn(updated);

        // Act
        ResponseEntity<WorkoutPlan> response = controller.update(1L, updated);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void update_shouldReturnNotFound_whenNotExists() {
        // Arrange
        when(service.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<WorkoutPlan> response = controller.update(1L, new WorkoutPlan());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void delete_shouldReturnNoContent_whenExists() {
        // Arrange
        when(service.findById(1L)).thenReturn(Optional.of(new WorkoutPlan()));

        // Act
        ResponseEntity<Void> response = controller.delete(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(1L);
    }

    @Test
    void delete_shouldReturnNotFound_whenNotExists() {
        // Arrange
        when(service.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = controller.delete(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}