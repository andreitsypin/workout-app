package com.workout.andreitsypin.repository;

import com.workout.andreitsypin.model.User;
import com.workout.andreitsypin.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUser(User user);
    List<Workout> findByDateBetween(LocalDate start, LocalDate end);
    List<Workout> findByUserAndDate(User user, LocalDate date);

    @Query("SELECT w FROM Workout w WHERE w.trainer.id = :trainerId AND w.date BETWEEN :start AND :end")
    List<Workout> findByTrainerAndDateRange(@Param("trainerId") Long trainerId,
                                            @Param("start") LocalDate start,
                                            @Param("end") LocalDate end);

    @Query("SELECT COUNT(w) FROM Workout w WHERE w.user.id = :userId AND w.date BETWEEN :start AND :end")
    Long countByUserAndDateRange(@Param("userId") Long userId,
                                 @Param("start") LocalDate start,
                                 @Param("end") LocalDate end);

    @Query("SELECT DISTINCT w.date FROM Workout w WHERE w.user.id = :userId ORDER BY w.date DESC")
    List<LocalDate> findDistinctDatesByUser(@Param("userId") Long userId);
}