package com.workout.andreitsypin.controller;

import com.workout.andreitsypin.model.Trainer;
import com.workout.andreitsypin.service.TrainerService;
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
class TrainerControllerTest {

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController controller;

    @Test
    void getAll_shouldReturnAllTrainers() {
        // Arrange
        Trainer trainer1 = new Trainer();
        Trainer trainer2 = new Trainer();
        when(trainerService.findAll()).thenReturn(Arrays.asList(trainer1, trainer2));

        // Act
        List<Trainer> result = controller.getAll();

        // Assert
        assertEquals(2, result.size());
        verify(trainerService, times(1)).findAll();
    }

    @Test
    void update_shouldUpdateAndReturnTrainer_whenExists() {
        // Arrange
        Trainer existing = new Trainer();
        existing.setId(1L);
        Trainer updated = new Trainer();
        updated.setId(1L);

        when(trainerService.findById(1L)).thenReturn(Optional.of(existing));
        when(trainerService.save(updated)).thenReturn(updated);

        // Act
        ResponseEntity<Trainer> response = controller.update(1L, updated);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void delete_shouldReturnNoContent_whenExists() {
        // Arrange
        when(trainerService.findById(1L)).thenReturn(Optional.of(new Trainer()));

        // Act
        ResponseEntity<Void> response = controller.delete(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(trainerService, times(1)).deleteById(1L);
    }
}