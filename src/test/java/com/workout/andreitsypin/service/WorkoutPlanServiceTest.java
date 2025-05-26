package com.workout.andreitsypin.service;

import com.workout.andreitsypin.model.WorkoutPlan;
import com.workout.andreitsypin.repository.WorkoutPlanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutPlanServiceTest {

    @Mock
    private WorkoutPlanRepository repository;

    @InjectMocks
    private WorkoutPlanService service;

    @Test
    void findAll_shouldReturnAllPlans() {
        // Arrange
        WorkoutPlan plan1 = new WorkoutPlan();
        WorkoutPlan plan2 = new WorkoutPlan();
        when(repository.findAll()).thenReturn(Arrays.asList(plan1, plan2));

        // Act
        List<WorkoutPlan> result = service.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void save_shouldReturnSavedPlan() {
        // Arrange
        WorkoutPlan plan = new WorkoutPlan();
        when(repository.save(plan)).thenReturn(plan);

        // Act
        WorkoutPlan result = service.save(plan);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).save(plan);
    }
}