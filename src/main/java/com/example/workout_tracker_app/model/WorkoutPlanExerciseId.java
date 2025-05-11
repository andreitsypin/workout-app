package com.example.workout_tracker_app.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class WorkoutPlanExerciseId implements Serializable {
    private Long workoutPlanId;
    private Long exerciseId;

    public WorkoutPlanExerciseId() {}

    public WorkoutPlanExerciseId(Long workoutPlanId, Long exerciseId) {
        this.workoutPlanId = workoutPlanId;
        this.exerciseId = exerciseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutPlanExerciseId that = (WorkoutPlanExerciseId) o;
        return Objects.equals(workoutPlanId, that.workoutPlanId) &&
                Objects.equals(exerciseId, that.exerciseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workoutPlanId, exerciseId);
    }
}