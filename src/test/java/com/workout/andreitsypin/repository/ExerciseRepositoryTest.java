package com.workout.andreitsypin.repository;

import com.workout.andreitsypin.model.Exercise;
import com.workout.andreitsypin.repository.ExerciseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ExerciseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Test
    public void testCreateExercise() {
        // Создание сущности без зависимостей
        Exercise exercise = new Exercise();
        exercise.setName("Deadlift");
        exercise.setDescription("Basic deadlift exercise");
        exercise.setMuscleGroup("Back");
        exercise.setDifficultyLevel(Exercise.Level.HARD);
        exercise.setEquipmentRequired("Barbell");

        // Сохранение
        Exercise savedExercise = exerciseRepository.save(exercise);
        entityManager.flush();
        entityManager.clear();

        // Проверка
        assertThat(savedExercise.getId()).isNotNull();
        assertThat(savedExercise.getCreatedAt()).isNotNull();
        assertThat(savedExercise.getUpdatedAt()).isNotNull();
    }

    @Test
    public void testReadExercise() {
        // Подготовка данных
        Exercise exercise = new Exercise();
        exercise.setName("Squats");
        exercise.setDescription("Bodyweight squats");
        exercise.setMuscleGroup("Legs");
        exercise.setDifficultyLevel(Exercise.Level.EASY);
        exercise.setEquipmentRequired("None");

        Exercise savedExercise = entityManager.persist(exercise);
        entityManager.flush();
        entityManager.clear();

        // Чтение
        Optional<Exercise> foundExercise = exerciseRepository.findById(savedExercise.getId());

        // Проверка
        assertThat(foundExercise).isPresent();
        assertThat(foundExercise.get().getName()).isEqualTo("Squats");
        assertThat(foundExercise.get().getMuscleGroup()).isEqualTo("Legs");
    }

    @Test
    public void testUpdateExercise() {
        // Подготовка данных
        Exercise exercise = new Exercise();
        exercise.setName("Bench Press");
        exercise.setDescription("Flat bench press");
        exercise.setMuscleGroup("Chest");
        exercise.setDifficultyLevel(Exercise.Level.MEDIUM);
        exercise.setEquipmentRequired("Bench, barbell");

        Exercise savedExercise = entityManager.persist(exercise);
        entityManager.flush();
        entityManager.clear();

        LocalDateTime originalUpdatedAt = savedExercise.getUpdatedAt();

        // Обновление
        Optional<Exercise> exerciseToUpdate = exerciseRepository.findById(savedExercise.getId());
        assertThat(exerciseToUpdate).isPresent();

        exerciseToUpdate.get().setDescription("Incline bench press");
        exerciseToUpdate.get().setDifficultyLevel(Exercise.Level.HARD);
        exerciseRepository.save(exerciseToUpdate.get());
        entityManager.flush();
        entityManager.clear();

        // Проверка
        Optional<Exercise> updatedExercise = exerciseRepository.findById(savedExercise.getId());
        assertThat(updatedExercise).isPresent();
        assertThat(updatedExercise.get().getDescription()).isEqualTo("Incline bench press");
        assertThat(updatedExercise.get().getDifficultyLevel()).isEqualTo(Exercise.Level.HARD);
        assertThat(updatedExercise.get().getUpdatedAt()).isAfter(originalUpdatedAt);
    }

    @Test
    public void testDeleteExercise() {
        // Подготовка данных
        Exercise exercise = new Exercise();
        exercise.setName("Pull-ups");
        exercise.setDescription("Basic pull-ups");
        exercise.setMuscleGroup("Back");
        exercise.setDifficultyLevel(Exercise.Level.HARD);
        exercise.setEquipmentRequired("Pull-up bar");

        Exercise savedExercise = entityManager.persist(exercise);
        entityManager.flush();
        entityManager.clear();

        // Удаление
        exerciseRepository.deleteById(savedExercise.getId());
        entityManager.flush();
        entityManager.clear();

        // Проверка
        Optional<Exercise> deletedExercise = exerciseRepository.findById(savedExercise.getId());
        assertThat(deletedExercise).isNotPresent();
    }
}