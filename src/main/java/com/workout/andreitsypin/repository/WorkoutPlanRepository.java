package com.workout.andreitsypin.repository;

import com.workout.andreitsypin.model.Trainer;
import com.workout.andreitsypin.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    List<WorkoutPlan> findByCreator(Trainer creator);
    List<WorkoutPlan> findByNameContainingIgnoreCase(String namePart);

    @Query("SELECT wp FROM WorkoutPlan wp JOIN wp.exerciseAssociations wpe WHERE wpe.exercise.id = :exerciseId")
    List<WorkoutPlan> findByExerciseId(@Param("exerciseId") Long exerciseId);

    @Query("SELECT wp FROM WorkoutPlan wp WHERE SIZE(wp.exerciseAssociations) > :minExercises")
    List<WorkoutPlan> findByMinExercisesCount(@Param("minExercises") int minExercises);

    List<WorkoutPlan> findByCreatorAndNameContainingIgnoreCase(Trainer creator, String namePart);
}