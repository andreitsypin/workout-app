package com.workout.andreitsypin.repository;

import com.workout.andreitsypin.model.WorkoutResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutResultRepository extends JpaRepository<WorkoutResult, Long> {
    // Пример: найти все результаты по конкретной тренировке
    List<WorkoutResult> findByWorkoutId(Long workoutId);

    // Пример: найти все результаты по упражнению
    List<WorkoutResult> findByExerciseId(Long exerciseId);
}
