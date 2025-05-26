package com.workout.andreitsypin.service;

import com.workout.andreitsypin.model.Trainer;
import com.workout.andreitsypin.repository.TrainerRepository;
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
class TrainerServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerService service;

    @Test
    void findAll_shouldReturnAllTrainers() {
        // Arrange
        Trainer trainer1 = new Trainer();
        Trainer trainer2 = new Trainer();
        when(trainerRepository.findAll()).thenReturn(Arrays.asList(trainer1, trainer2));

        // Act
        List<Trainer> result = service.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(trainerRepository, times(1)).findAll();
    }

    @Test
    void save_shouldReturnSavedTrainer() {
        // Arrange
        Trainer trainer = new Trainer();
        when(trainerRepository.save(trainer)).thenReturn(trainer);

        // Act
        Trainer result = service.save(trainer);

        // Assert
        assertNotNull(result);
        verify(trainerRepository, times(1)).save(trainer);
    }

    @Test
    void deleteById_shouldCallRepository() {
        // Act
        service.deleteById(1L);

        // Assert
        verify(trainerRepository, times(1)).deleteById(1L);
    }
}