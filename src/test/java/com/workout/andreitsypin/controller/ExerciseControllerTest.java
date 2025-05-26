package com.workout.andreitsypin.controller;

import com.workout.andreitsypin.model.Exercise;
import com.workout.andreitsypin.service.ExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExerciseControllerTest {

    private ExerciseService service;
    private ExerciseController controller;

    @BeforeEach
    void setUp() {
        service = mock(ExerciseService.class);
        controller = new ExerciseController(service);
    }

    @Test
    void testGetAll() {
        List<Exercise> exercises = Arrays.asList(new Exercise(), new Exercise());
        when(service.findAll()).thenReturn(exercises);

        List<Exercise> result = controller.getAll();

        assertEquals(2, result.size());
        verify(service).findAll();
    }

    @Test
    void testGetById_found() {
        Exercise exercise = new Exercise();
        exercise.setId(1L);
        when(service.findById(1L)).thenReturn(Optional.of(exercise));

        ResponseEntity<Exercise> response = controller.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(exercise, response.getBody());
    }

    @Test
    void testGetById_notFound() {
        when(service.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Exercise> response = controller.getById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate() {
        Exercise exercise = new Exercise();
        when(service.save(exercise)).thenReturn(exercise);

        Exercise result = controller.create(exercise);

        assertEquals(exercise, result);
        verify(service).save(exercise);
    }

    @Test
    void testUpdate_found() {
        Exercise updated = new Exercise();
        updated.setName("Updated");

        when(service.findById(1L)).thenReturn(Optional.of(new Exercise()));
        when(service.save(updated)).thenReturn(updated);

        ResponseEntity<Exercise> response = controller.update(1L, updated);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated", response.getBody().getName());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testUpdate_notFound() {
        Exercise updated = new Exercise();
        when(service.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Exercise> response = controller.update(1L, updated);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDelete_found() {
        when(service.findById(1L)).thenReturn(Optional.of(new Exercise()));

        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(service).delete(1L);
    }

    @Test
    void testDelete_notFound() {
        when(service.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(404, response.getStatusCodeValue());
    }
}
