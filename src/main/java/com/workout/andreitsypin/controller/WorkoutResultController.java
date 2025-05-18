package com.workout.andreitsypin.controller;

import com.workout.andreitsypin.model.WorkoutResult;
import com.workout.andreitsypin.service.WorkoutResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-results")
public class WorkoutResultController {
    private final WorkoutResultService service;

    public WorkoutResultController(WorkoutResultService service) {
        this.service = service;
    }

    @GetMapping
    public List<WorkoutResult> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutResult> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public WorkoutResult create(@RequestBody WorkoutResult result) {
        return service.save(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutResult> update(@PathVariable Long id, @RequestBody WorkoutResult updated) {
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
