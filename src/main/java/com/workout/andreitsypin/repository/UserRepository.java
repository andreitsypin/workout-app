package com.workout.andreitsypin.repository;

import com.workout.andreitsypin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRole(User.Role role);

    @Query("SELECT u FROM User u WHERE u.height BETWEEN :minHeight AND :maxHeight")
    List<User> findByHeightRange(@Param("minHeight") Integer minHeight,
                                 @Param("maxHeight") Integer maxHeight);

    @Query("SELECT u FROM User u WHERE u.birthDate BETWEEN :startDate AND :endDate")
    List<User> findByBirthDateBetween(@Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);

    boolean existsByEmail(String email);
}