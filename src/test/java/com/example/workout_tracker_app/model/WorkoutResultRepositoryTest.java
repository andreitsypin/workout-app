package com.example.workout_tracker_app.model;

import com.example.workout_tracker_app.repository.WorkoutResultRepository;
import com.example.workout_tracker_app.service.WorkoutResultService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
public class WorkoutResultRepositoryTest {

    @InjectMocks
    private WorkoutResultService workoutResultService;

    @Mock
    private WorkoutResultRepository workoutResultRepository;

    @Test
    void testWorkoutResultCRUD() {
        WorkoutResult result = new WorkoutResult();
        result.setId(1L);
        result.setWorkoutId(10L);
        result.setExerciseId(5L);
        result.setReps(10);
        result.setWeight(50.0);
        result.setDuration(30);

        workoutResultService.saveWorkoutResult(result);

        when(workoutResultRepository.findById(1L)).thenReturn(Optional.of(result));
        WorkoutResult found = workoutResultService.findById(1L);

        assertEquals(10, found.getReps());

        found.setReps(15);
        assertEquals(15, workoutResultService.findById(1L).getReps());

        doNothing().when(workoutResultRepository).deleteById(1L);
        when(workoutResultRepository.findById(1L)).thenReturn(Optional.empty());

        workoutResultService.deleteById(1L);
        assertNull(workoutResultService.findById(1L));
    }
}
