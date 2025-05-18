package com.workout.andreitsypin.controller;

import com.workout.andreitsypin.model.WorkoutPlanExercise;
import com.workout.andreitsypin.service.WorkoutPlanExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-plan-exercises")
public class WorkoutPlanExerciseController {
    private final WorkoutPlanExerciseService service;

    public WorkoutPlanExerciseController(WorkoutPlanExerciseService service) {
        this.service = service;
    }

    @GetMapping
    public List<WorkoutPlanExercise> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlanExercise> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public WorkoutPlanExercise create(@RequestBody WorkoutPlanExercise wpe) {
        return service.save(wpe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutPlanExercise> update(@PathVariable Long id, @RequestBody WorkoutPlanExercise updated) {
        return service.findById(id).map(existing -> {
            updated.setId(id);
            return ResponseEntity.ok(service.save(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
