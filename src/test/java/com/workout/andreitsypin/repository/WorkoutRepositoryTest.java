package com.workout.andreitsypin.repository;

import com.workout.andreitsypin.model.Trainer;
import com.workout.andreitsypin.model.User;
import com.workout.andreitsypin.model.Workout;
import com.workout.andreitsypin.model.WorkoutPlan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.workout.andreitsypin.model.User.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class WorkoutRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Test
    public void testCreateWorkoutWithFullDependencies() {
        // Создаем User со всеми полями (для creator плана тренировки)
        User planCreator = new User();
        planCreator.setLogin("plancreator");
        planCreator.setEmail("creator@example.com");
        planCreator.setName("Создатель Плана");
        planCreator.setPassword("creatorpass");
        planCreator.setRole(USER);
        planCreator.setBirthDate(LocalDate.now().minusYears(30));
        planCreator.setHeight(175);
        planCreator.setWeight(70);
        planCreator.setCreatedAt(LocalDateTime.now());
        planCreator = entityManager.persist(planCreator);

        // Создаем User со всеми полями (для участника тренировки)
        User workoutUser = new User();
        workoutUser.setLogin("workoutuser");
        workoutUser.setEmail("user@example.com");
        workoutUser.setName("Участник Тренировки");
        workoutUser.setPassword("userpass");
        workoutUser.setRole(USER);
        workoutUser.setBirthDate(LocalDate.now().minusYears(25));
        workoutUser.setHeight(180);
        workoutUser.setWeight(80);
        workoutUser.setCreatedAt(LocalDateTime.now());
        workoutUser = entityManager.persist(workoutUser);

        // Создаем Trainer со всеми полями
        Trainer trainer = new Trainer();
        trainer.setSpecialization("Fitness");
        trainer.setCertification("ACE Certified");
        trainer.setExperienceYears(5);
        trainer.setRating(4.8);
        trainer = entityManager.persist(trainer);

        // Создаем WorkoutPlan с обязательным creator
        WorkoutPlan plan = new WorkoutPlan();
        plan.setName("Test Plan");
        plan.setDescription("Test Description");
        plan.setCreator(trainer); // Устанавливаем создателя
        plan = entityManager.persist(plan);

        // Создаем Workout
        Workout workout = new Workout();
        workout.setUser(workoutUser);
        workout.setTrainer(trainer);
        workout.setPlan(plan);
        workout.setDate(LocalDate.now());

        // Сохраняем
        Workout savedWorkout = workoutRepository.save(workout);
        entityManager.flush();
        entityManager.clear();

        // Проверяем
        Optional<Workout> foundWorkout = workoutRepository.findById(savedWorkout.getId());
        assertThat(foundWorkout).isPresent();
        assertThat(foundWorkout.get().getUser().getLogin()).isEqualTo("workoutuser"); // Исправлено на актуальный логин
        assertThat(foundWorkout.get().getTrainer().getSpecialization()).isEqualTo("Fitness");
        assertThat(foundWorkout.get().getPlan().getName()).isEqualTo("Test Plan");

    }

    @Test
    public void testReadWorkoutWithRelations() {
        // Создаем пользователя с уникальными данными
        User user = new User();
        user.setLogin("read_test_user");
        user.setEmail("readtestuser@example.com");
        user.setName("Read Test User");
        user.setPassword("readtestpass");
        user.setRole(USER);
        user.setBirthDate(LocalDate.of(1992, 3, 10));
        user.setHeight(175);
        user.setWeight(70);
        user.setCreatedAt(LocalDateTime.now());
        user = entityManager.persist(user);

        // Создаем тренера с уникальными данными
        Trainer trainer = new Trainer();
        trainer.setSpecialization("Read Test Specialization");
        trainer.setCertification("Read Test Certified");
        trainer.setExperienceYears(4);
        trainer.setRating(4.7);
        trainer = entityManager.persist(trainer);

        // Создаем план тренировки с уникальными данными
        WorkoutPlan plan = new WorkoutPlan();
        plan.setName("Read Test Plan");
        plan.setDescription("Plan for read test");
        plan.setCreator(trainer); // Устанавливаем создателя
        plan = entityManager.persist(plan);

        // Создаем тренировку
        Workout workout = new Workout();
        workout.setUser(user);
        workout.setTrainer(trainer);
        workout.setPlan(plan);
        workout.setDate(LocalDate.of(2023, 6, 15));

        Workout savedWorkout = entityManager.persist(workout);
        entityManager.flush();
        entityManager.clear();

        // Чтение тренировки
        Optional<Workout> foundWorkout = workoutRepository.findById(savedWorkout.getId());

        // Проверки
        assertThat(foundWorkout).isPresent();

        // Проверка основных полей
        assertThat(foundWorkout.get().getDate()).isEqualTo(LocalDate.of(2023, 6, 15));

        // Проверка связей
        assertThat(foundWorkout.get().getUser().getEmail()).isEqualTo("readtestuser@example.com");
        assertThat(foundWorkout.get().getUser().getLogin()).isEqualTo("read_test_user");

        assertThat(foundWorkout.get().getTrainer().getCertification())
                .isEqualTo("Read Test Certified");
        assertThat(foundWorkout.get().getTrainer().getSpecialization())
                .isEqualTo("Read Test Specialization");

        assertThat(foundWorkout.get().getPlan().getName()).isEqualTo("Read Test Plan");
    }

    @Test
    public void testUpdateWorkoutRelations() {
        // Создаем первого пользователя с уникальными данными
        User user1 = new User();
        user1.setLogin("user1_update_test");
        user1.setEmail("user1_update@test.com");
        user1.setName("User One Update Test");
        user1.setPassword("password1");
        user1.setRole(USER);
        user1.setBirthDate(LocalDate.of(1990, 5, 15));
        user1.setHeight(170);
        user1.setWeight(65);
        user1.setCreatedAt(LocalDateTime.now());
        user1 = entityManager.persist(user1);

        // Создаем второго пользователя с уникальными данными
        User user2 = new User();
        user2.setLogin("user2_update_test");
        user2.setEmail("user2_update@test.com");
        user2.setName("User Two Update Test");
        user2.setPassword("password2");
        user2.setRole(USER);
        user2.setBirthDate(LocalDate.of(1985, 8, 20));
        user2.setHeight(180);
        user2.setWeight(75);
        user2.setCreatedAt(LocalDateTime.now());
        user2 = entityManager.persist(user2);

        // Создаем тренера с уникальными данными
        Trainer trainer = new Trainer();
        trainer.setSpecialization("Update Test Specialization");
        trainer.setCertification("Update Test Certification");
        trainer.setExperienceYears(3);
        trainer.setRating(4.5);
        trainer = entityManager.persist(trainer);

        // Создаем план тренировки с уникальными данными
        WorkoutPlan plan = new WorkoutPlan();
        plan.setName("Update Test Plan");
        plan.setDescription("Plan for update test");
        plan.setCreator(trainer); // Устанавливаем создателя
        plan = entityManager.persist(plan);

        // Создаем тренировку
        Workout workout = new Workout();
        workout.setUser(user1);
        workout.setTrainer(trainer);
        workout.setPlan(plan);
        workout.setDate(LocalDate.of(2023, 1, 1));

        Workout savedWorkout = entityManager.persist(workout);
        entityManager.flush();
        entityManager.clear();

        // Обновляем тренировку
        Optional<Workout> workoutToUpdate = workoutRepository.findById(savedWorkout.getId());
        assertThat(workoutToUpdate).isPresent();

        // Меняем пользователя и дату
        workoutToUpdate.get().setUser(user2);
        workoutToUpdate.get().setDate(LocalDate.of(2023, 12, 31));
        workoutRepository.save(workoutToUpdate.get());
        entityManager.flush();
        entityManager.clear();

        // Проверяем обновленные данные
        Optional<Workout> updatedWorkout = workoutRepository.findById(savedWorkout.getId());
        assertThat(updatedWorkout).isPresent();

        // Проверяем пользователя
        assertThat(updatedWorkout.get().getUser().getLogin()).isEqualTo("user2_update_test");
        assertThat(updatedWorkout.get().getUser().getEmail()).isEqualTo("user2_update@test.com");

        // Проверяем дату
        assertThat(updatedWorkout.get().getDate()).isEqualTo(LocalDate.of(2023, 12, 31));

        // Проверяем, что другие связи не изменились
        assertThat(updatedWorkout.get().getTrainer().getSpecialization()).isEqualTo("Update Test Specialization");
        assertThat(updatedWorkout.get().getPlan().getName()).isEqualTo("Update Test Plan");
    }

    @Test
    public void testDeleteWorkoutWithRelations() {
        // Подготовка данных
        User user = createFullUser();
        Trainer trainer = createFullTrainer();
        WorkoutPlan plan = createFullWorkoutPlan();

        Workout workout = new Workout();
        workout.setUser(user);
        workout.setTrainer(trainer);
        workout.setPlan(plan);
        workout.setDate(LocalDate.now());

        Workout savedWorkout = entityManager.persist(workout);
        entityManager.flush();
        entityManager.clear();

        // Удаление
        workoutRepository.deleteById(savedWorkout.getId());
        entityManager.flush();
        entityManager.clear();

        // Проверка
        Optional<Workout> deletedWorkout = workoutRepository.findById(savedWorkout.getId());
        assertThat(deletedWorkout).isNotPresent();

        // Проверяем, что связанные сущности не удалились
        Optional<User> userAfterDelete = userRepository.findById(user.getId());
        Optional<Trainer> trainerAfterDelete = trainerRepository.findById(trainer.getId());
        Optional<WorkoutPlan> planAfterDelete = workoutPlanRepository.findById(plan.getId());

        assertThat(userAfterDelete).isPresent();
        assertThat(trainerAfterDelete).isPresent();
        assertThat(planAfterDelete).isPresent();
    }

    private User createFullUser() {
        User user = new User();
        user.setLogin("todelete");
        user.setEmail("delete@example.com");
        user.setName("Удаляемый Пользователь");
        user.setPassword("qwert");
        user.setRole(USER);
        user.setBirthDate(LocalDate.now());
        user.setHeight(180);
        user.setWeight(80);
        user.setCreatedAt(LocalDateTime.now());

        return entityManager.persist(user);
    }

    private Trainer createFullTrainer() {
        Trainer trainer = new Trainer();
        trainer.setSpecialization("Strength Training");
        trainer.setCertification("NASM Certified");
        trainer.setExperienceYears(7);
        trainer.setRating(4.9);
        return entityManager.persist(trainer);
    }

    private WorkoutPlan createFullWorkoutPlan() {
        WorkoutPlan plan = new WorkoutPlan();
        plan.setName("Full Workout Plan");
        plan.setDescription("Comprehensive training plan");
        Trainer trainer = new Trainer();
        trainer.setSpecialization("Strength training");
        trainer.setCertification("NASM Certified");
        trainer.setExperienceYears(5);
        trainer.setRating(4.8);

        // Сохранение
        Trainer savedTrainer = trainerRepository.save(trainer);
        plan.setCreator(trainer);
        return entityManager.persist(plan);
    }

    // Добавляем зависимости репозиториев для проверки сохранности связанных сущностей
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;
}