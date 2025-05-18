package com.workout.andreitsypin.repository;

import com.workout.andreitsypin.model.Trainer;
import com.workout.andreitsypin.model.User;
import com.workout.andreitsypin.model.WorkoutPlan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class WorkoutPlanRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;

    @Test
    public void whenSaveWorkoutPlan_thenCanFindById() {
        // Создаем тренера (creator)
        Trainer creator = createTestTrainer("creator@test.com");

        // Создаем план тренировки
        WorkoutPlan plan = new WorkoutPlan();
        plan.setName("Beginner Plan");
        plan.setDescription("12-week beginner program");
        plan.setCreator(creator); // Устанавливаем тренера как создателя
        plan = entityManager.persist(plan);

        entityManager.flush();
        entityManager.clear();

        // Ищем план
        WorkoutPlan found = workoutPlanRepository.findById(plan.getId()).orElse(null);

        // Проверки
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Beginner Plan");
        assertThat(found.getCreator().getCertification()).isEqualTo("ACE Certified");
    }

    @Test
    public void testFindByNameContaining() {
        // Создаем тренера
        Trainer creator = createTestTrainer("test@test.com");

        // Создаем несколько планов
        WorkoutPlan plan1 = new WorkoutPlan();
        plan1.setName("Advanced Strength Program");
        plan1.setCreator(creator);
        entityManager.persist(plan1);

        WorkoutPlan plan2 = new WorkoutPlan();
        plan2.setName("Beginner Cardio Plan");
        plan2.setCreator(creator);
        entityManager.persist(plan2);

        entityManager.flush();
        entityManager.clear();

        // Ищем по части имени
        List<WorkoutPlan> foundPlans = workoutPlanRepository.findByNameContainingIgnoreCase("Program");

        // Проверки
        assertThat(foundPlans).hasSize(1);
        assertThat(foundPlans.get(0).getName()).isEqualTo("Advanced Strength Program");
    }

    private Trainer createTestTrainer(String email) {
        Trainer trainer = new Trainer();
        trainer.setSpecialization("Fitness");
        trainer.setCertification("ACE Certified");
        trainer.setExperienceYears(5);
        trainer.setRating(4.8);
        return entityManager.persist(trainer);
    }

    // Дополнительный метод, если нужно создавать User для других тестов
    private User createTestUser() {
        User user = new User();
        user.setLogin("test_user");
        user.setEmail("user@test.com");
        user.setName("Test User");
        user.setPassword("password");
        user.setRole(User.Role.USER);
        user.setBirthDate(LocalDateTime.now().toLocalDate());
        user.setHeight(180);
        user.setWeight(75);
        user.setCreatedAt(LocalDateTime.now());
        return entityManager.persist(user);
    }
}