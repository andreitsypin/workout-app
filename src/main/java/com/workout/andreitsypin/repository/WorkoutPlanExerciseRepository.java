package com.workout.andreitsypin.repository;


import com.workout.andreitsypin.model.Exercise;
import com.workout.andreitsypin.model.WorkoutPlan;
import com.workout.andreitsypin.model.WorkoutPlanExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutPlanExerciseRepository extends JpaRepository<WorkoutPlanExercise, Long> {
    List<WorkoutPlanExercise> findByWorkoutPlan(WorkoutPlan plan);
    List<WorkoutPlanExercise> findByExercise(Exercise exercise);

    @Query("SELECT wpe FROM WorkoutPlanExercise wpe WHERE wpe.workoutPlan.id = :planId AND wpe.exercise.id = :exerciseId")
    Optional<WorkoutPlanExercise> findByPlanAndExercise(@Param("planId") Long planId,
                                                        @Param("exerciseId") Long exerciseId);

    @Modifying
    @Query("DELETE FROM WorkoutPlanExercise wpe WHERE wpe.workoutPlan.id = :planId")
    void deleteAllByWorkoutPlanId(@Param("planId") Long planId);
}