package com.workout.andreitsypin.service;

import com.workout.andreitsypin.model.WorkoutPlanExercise;
import com.workout.andreitsypin.repository.WorkoutPlanExerciseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutPlanExerciseServiceTest {

    @Mock
    private WorkoutPlanExerciseRepository repository;

    @InjectMocks
    private WorkoutPlanExerciseService service;

    @Test
    void findById_shouldReturnAssociation_whenExists() {
        // Arrange
        WorkoutPlanExercise wpe = new WorkoutPlanExercise();
        wpe.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(wpe));

        // Act
        Optional<WorkoutPlanExercise> result = service.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void save_shouldReturnSavedAssociation() {
        // Arrange
        WorkoutPlanExercise wpe = new WorkoutPlanExercise();
        when(repository.save(wpe)).thenReturn(wpe);

        // Act
        WorkoutPlanExercise result = service.save(wpe);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).save(wpe);
    }
}