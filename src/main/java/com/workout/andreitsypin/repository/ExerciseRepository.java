package com.workout.andreitsypin.repository;

import com.workout.andreitsypin.model.Exercise;
import com.workout.andreitsypin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByMuscleGroup(String muscleGroup);
    List<Exercise> findByDifficultyLevel(Exercise.Level level);
    List<Exercise> findByEquipmentRequired(String equipment);
    List<Exercise> findByCreator(User creator);
    List<Exercise> findByNameContainingIgnoreCase(String namePart);

    @Query("SELECT DISTINCT e.muscleGroup FROM Exercise e")
    List<String> findAllDistinctMuscleGroups();

    @Query("SELECT e FROM Exercise e JOIN e.planAssociations pa WHERE pa.workoutPlan.id = :planId")
    List<Exercise> findByWorkoutPlanId(@Param("planId") Long planId);
}