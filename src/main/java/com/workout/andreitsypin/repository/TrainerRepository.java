package com.workout.andreitsypin.repository;

import com.workout.andreitsypin.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    List<Trainer> findBySpecialization(String specialization);
    List<Trainer> findByRatingGreaterThanEqual(Double minRating);
    List<Trainer> findByExperienceYearsGreaterThanEqual(Integer years);

    @Query("SELECT t FROM Trainer t WHERE SIZE(t.conductedWorkouts) > :minWorkouts")
    List<Trainer> findMostActiveTrainers(@Param("minWorkouts") int minWorkouts);

}