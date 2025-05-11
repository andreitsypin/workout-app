package com.example.workout_tracker_app.model;

import com.example.workout_tracker_app.repository.ExerciseRepository;
import com.example.workout_tracker_app.service.ExerciseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
public class ExerciseRepositoryTest {

    @InjectMocks
    private ExerciseService exerciseService;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Test
    void testExerciseCRUD() {
        Exercise exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Push-up");
        exercise.setDescription("Basic push-up");
        exercise.setMuscleGroup("Chest");

        exerciseService.saveExercise(exercise);

        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        Exercise found = exerciseService.findById(1L);

        assertEquals("Push-up", found.getName());

        found.setName("Modified Push-up");
        assertEquals("Modified Push-up", exerciseService.findById(1L).getName());

        doNothing().when(exerciseRepository).deleteById(1L);
        when(exerciseRepository.findById(1L)).thenReturn(Optional.empty());

        exerciseService.deleteById(1L);
        assertNull(exerciseService.findById(1L));
    }
}
