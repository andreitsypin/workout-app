package com.workout.andreitsypin.repository;

import com.workout.andreitsypin.model.Exercise;
import com.workout.andreitsypin.model.Trainer;
import com.workout.andreitsypin.model.User;
import com.workout.andreitsypin.model.WorkoutPlan;
import com.workout.andreitsypin.model.WorkoutPlanExercise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WorkoutPlanExerciseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WorkoutPlanExerciseRepository workoutPlanExerciseRepository;

    @Test
    public void testCreateWorkoutPlanExercise() {
        // Создаем необходимые сущности
        User user = createTestUser();
        Trainer trainer = createTestTrainer(user);
        WorkoutPlan plan = createTestWorkoutPlan(trainer);
        Exercise exercise = createTestExercise(user);

        // Создаем связь
        WorkoutPlanExercise wpe = new WorkoutPlanExercise();
        wpe.setWorkoutPlan(plan);
        wpe.setExercise(exercise);

        // Сохраняем
        WorkoutPlanExercise saved = workoutPlanExerciseRepository.save(wpe);
        entityManager.flush();
        entityManager.clear();

        // Проверяем
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    public void testReadWorkoutPlanExercise() {
        // Подготовка данных
        User user = createTestUser();
        Trainer trainer = createTestTrainer(user);
        WorkoutPlan plan = createTestWorkoutPlan(trainer);
        Exercise exercise = createTestExercise(user);

        WorkoutPlanExercise wpe = new WorkoutPlanExercise();
        wpe.setWorkoutPlan(plan);
        wpe.setExercise(exercise);
        WorkoutPlanExercise saved = entityManager.persist(wpe);
        entityManager.flush();
        entityManager.clear();

        // Чтение
        Optional<WorkoutPlanExercise> found = workoutPlanExerciseRepository.findById(saved.getId());

        // Проверки
        assertThat(found).isPresent();
        assertThat(found.get().getWorkoutPlan().getName()).isEqualTo("Test Plan");
        assertThat(found.get().getExercise().getName()).isEqualTo("Test Exercise");
    }

    @Test
    public void testUpdateWorkoutPlanExercise() {
        // Подготовка данных
        User user = createTestUser();
        Trainer trainer = createTestTrainer(user);
        WorkoutPlan plan1 = createTestWorkoutPlan(trainer);
        WorkoutPlan plan2 = createTestWorkoutPlan(trainer);
        plan2.setName("Updated Plan");
        Exercise exercise = createTestExercise(user);

        WorkoutPlanExercise wpe = new WorkoutPlanExercise();
        wpe.setWorkoutPlan(plan1);
        wpe.setExercise(exercise);
        WorkoutPlanExercise saved = entityManager.persist(wpe);
        entityManager.flush();
        entityManager.clear();

        // Обновление
        Optional<WorkoutPlanExercise> toUpdate = workoutPlanExerciseRepository.findById(saved.getId());
        assertThat(toUpdate).isPresent();
        toUpdate.get().setWorkoutPlan(plan2);
        workoutPlanExerciseRepository.save(toUpdate.get());
        entityManager.flush();
        entityManager.clear();

        // Проверка
        Optional<WorkoutPlanExercise> updated = workoutPlanExerciseRepository.findById(saved.getId());
        assertThat(updated).isPresent();
        assertThat(updated.get().getWorkoutPlan().getName()).isEqualTo("Updated Plan");
    }

    @Test
    public void testDeleteWorkoutPlanExercise() {
        // Подготовка данных
        User user = createTestUser();
        Trainer trainer = createTestTrainer(user);
        WorkoutPlan plan = createTestWorkoutPlan(trainer);
        Exercise exercise = createTestExercise(user);

        WorkoutPlanExercise wpe = new WorkoutPlanExercise();
        wpe.setWorkoutPlan(plan);
        wpe.setExercise(exercise);
        WorkoutPlanExercise saved = entityManager.persist(wpe);
        entityManager.flush();
        entityManager.clear();

        // Удаление
        workoutPlanExerciseRepository.deleteById(saved.getId());
        entityManager.flush();
        entityManager.clear();

        // Проверка
        Optional<WorkoutPlanExercise> deleted = workoutPlanExerciseRepository.findById(saved.getId());
        assertThat(deleted).isNotPresent();
    }

    // Вспомогательные методы для создания тестовых данных
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

    private Trainer createTestTrainer(User user) {
        Trainer trainer = new Trainer();
        trainer.setSpecialization("Fitness");
        trainer.setCertification("ACE Certified");
        trainer.setExperienceYears(5);
        trainer.setRating(4.8);
        return entityManager.persist(trainer);
    }

    private WorkoutPlan createTestWorkoutPlan(Trainer creator) {
        WorkoutPlan plan = new WorkoutPlan();
        plan.setName("Test Plan");
        plan.setDescription("Test Description");
        plan.setCreator(creator); // Устанавливаем создателя (Trainer)
        return entityManager.persist(plan);
    }

    private Exercise createTestExercise(User creator) {
        Exercise exercise = new Exercise();
        exercise.setName("Test Exercise");
        exercise.setDescription("Test Description");
        exercise.setMuscleGroup("Test Muscle");
        exercise.setDifficultyLevel(Exercise.Level.MEDIUM);
        exercise.setEquipmentRequired("None");
        exercise.setCreator(creator);
        return entityManager.persist(exercise);
    }
}