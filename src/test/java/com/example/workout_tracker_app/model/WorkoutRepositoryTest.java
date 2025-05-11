package com.example.workout_tracker_app.model;

import com.example.workout_tracker_app.repository.WorkoutRepository;
import com.example.workout_tracker_app.service.WorkoutService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
public class WorkoutRepositoryTest {

    @InjectMocks
    private WorkoutService workoutService;

    @Mock
    private WorkoutRepository workoutRepository;

    @Test
    void testWorkoutCRUD() {
        Workout workout = new Workout();
        workout.setId(1L);
        workout.setUserId(1L);
        workout.setCoachId(2L);
        workout.setPlanId(3L);
        workout.setDate(LocalDate.now());

        workoutService.saveWorkout(workout);

        when(workoutRepository.findById(1L)).thenReturn(Optional.of(workout));
        Workout found = workoutService.findById(1L);

        assertNotNull(found);

        found.setCoachId(99L);
        assertEquals(99L, workoutService.findById(1L).getCoachId());

        doNothing().when(workoutRepository).deleteById(1L);
        when(workoutRepository.findById(1L)).thenReturn(Optional.empty());

        workoutService.deleteById(1L);
        assertNull(workoutService.findById(1L));
    }
}
