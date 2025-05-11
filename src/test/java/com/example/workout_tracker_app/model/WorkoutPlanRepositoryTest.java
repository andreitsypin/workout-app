package com.example.workout_tracker_app.model;

import com.example.workout_tracker_app.repository.WorkoutPlanRepository;
import com.example.workout_tracker_app.service.WorkoutPlanService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
public class WorkoutPlanRepositoryTest {

    @InjectMocks
    private WorkoutPlanService workoutPlanService;

    @Mock
    private WorkoutPlanRepository workoutPlanRepository;

    @Test
    void testWorkoutPlanCRUD() {
        WorkoutPlan plan = new WorkoutPlan();
        plan.setId(1L);
        plan.setName("Full Body Plan");
        plan.setDescription("Workout plan for full body");

        workoutPlanService.saveWorkoutPlan(plan);

        when(workoutPlanRepository.findById(1L)).thenReturn(Optional.of(plan));
        WorkoutPlan found = workoutPlanService.findById(1L);

        assertEquals("Full Body Plan", found.getName());

        found.setName("Updated Plan");
        assertEquals("Updated Plan", workoutPlanService.findById(1L).getName());

        doNothing().when(workoutPlanRepository).deleteById(1L);
        when(workoutPlanRepository.findById(1L)).thenReturn(Optional.empty());

        workoutPlanService.deleteById(1L);
        assertNull(workoutPlanService.findById(1L));
    }
}
