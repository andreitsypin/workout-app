package com.workout.andreitsypin.service;

import com.workout.andreitsypin.model.WorkoutResult;
import com.workout.andreitsypin.repository.WorkoutResultRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutResultServiceTest {

    @Mock
    private WorkoutResultRepository repository;

    @InjectMocks
    private WorkoutResultService service;

    @Test
    void save_shouldReturnSavedResult() {
        // Arrange
        WorkoutResult result = new WorkoutResult();
        when(repository.save(result)).thenReturn(result);

        // Act
        WorkoutResult saved = service.save(result);

        // Assert
        assertNotNull(saved);
        verify(repository, times(1)).save(result);
    }

    @Test
    void delete_shouldCallRepository() {
        // Act
        service.delete(1L);

        // Assert
        verify(repository, times(1)).deleteById(1L);
    }
}