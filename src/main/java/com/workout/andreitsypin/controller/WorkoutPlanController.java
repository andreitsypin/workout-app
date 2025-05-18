package com.workout.andreitsypin.controller;

import com.workout.andreitsypin.model.WorkoutPlan;
import com.workout.andreitsypin.service.WorkoutPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-plans")
public class WorkoutPlanController {
    private final WorkoutPlanService service;

    public WorkoutPlanController(WorkoutPlanService service) {
        this.service = service;
    }

    @GetMapping
    public List<WorkoutPlan> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlan> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public WorkoutPlan create(@RequestBody WorkoutPlan plan) {
        return service.save(plan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutPlan> update(@PathVariable Long id, @RequestBody WorkoutPlan updated) {
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
