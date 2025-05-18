package com.workout.andreitsypin.repository;

import com.workout.andreitsypin.model.Trainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class TrainerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TrainerRepository trainerRepository;

    @Test
    public void testCreateTrainer() {
        // Создание сущности без зависимостей
        Trainer trainer = new Trainer();
        trainer.setSpecialization("Strength training");
        trainer.setCertification("NASM Certified");
        trainer.setExperienceYears(5);
        trainer.setRating(4.8);

        // Сохранение
        Trainer savedTrainer = trainerRepository.save(trainer);
        entityManager.flush();
        entityManager.clear();

        // Проверка
        assertThat(savedTrainer.getId()).isNotNull();
        assertThat(savedTrainer.getSpecialization()).isEqualTo("Strength training");
        assertThat(savedTrainer.getRating()).isEqualTo(4.8);
    }

    @Test
    public void testReadTrainer() {
        // Подготовка данных
        Trainer trainer = new Trainer();
        trainer.setSpecialization("Yoga");
        trainer.setCertification("RYT 200");
        trainer.setExperienceYears(3);
        trainer.setRating(4.5);

        Trainer savedTrainer = entityManager.persist(trainer);
        entityManager.flush();
        entityManager.clear();

        // Чтение
        Optional<Trainer> foundTrainer = trainerRepository.findById(savedTrainer.getId());

        // Проверка
        assertThat(foundTrainer).isPresent();
        assertThat(foundTrainer.get().getCertification()).isEqualTo("RYT 200");
        assertThat(foundTrainer.get().getExperienceYears()).isEqualTo(3);
    }

    @Test
    public void testUpdateTrainer() {
        // Подготовка данных
        Trainer trainer = new Trainer();
        trainer.setSpecialization("CrossFit");
        trainer.setCertification("CF-L1");
        trainer.setExperienceYears(2);
        trainer.setRating(4.2);

        Trainer savedTrainer = entityManager.persist(trainer);
        entityManager.flush();
        entityManager.clear();

        // Обновление
        Optional<Trainer> trainerToUpdate = trainerRepository.findById(savedTrainer.getId());
        assertThat(trainerToUpdate).isPresent();

        trainerToUpdate.get().setExperienceYears(3);
        trainerToUpdate.get().setRating(4.5);
        trainerRepository.save(trainerToUpdate.get());
        entityManager.flush();
        entityManager.clear();

        // Проверка
        Optional<Trainer> updatedTrainer = trainerRepository.findById(savedTrainer.getId());
        assertThat(updatedTrainer).isPresent();
        assertThat(updatedTrainer.get().getExperienceYears()).isEqualTo(3);
        assertThat(updatedTrainer.get().getRating()).isEqualTo(4.5);
    }

    @Test
    public void testDeleteTrainer() {
        // Подготовка данных
        Trainer trainer = new Trainer();
        trainer.setSpecialization("Pilates");
        trainer.setCertification("PMA Certified");
        trainer.setExperienceYears(4);
        trainer.setRating(4.7);

        Trainer savedTrainer = entityManager.persist(trainer);
        entityManager.flush();
        entityManager.clear();

        // Удаление
        trainerRepository.deleteById(savedTrainer.getId());
        entityManager.flush();
        entityManager.clear();

        // Проверка
        Optional<Trainer> deletedTrainer = trainerRepository.findById(savedTrainer.getId());
        assertThat(deletedTrainer).isNotPresent();
    }

    @Test
    public void testFindBySpecialization() {
        // Подготовка данных
        Trainer trainer1 = new Trainer();
        trainer1.setSpecialization("Bodybuilding");
        trainer1.setCertification("ACE Certified");
        trainer1.setExperienceYears(6);
        trainer1.setRating(4.9);

        Trainer trainer2 = new Trainer();
        trainer2.setSpecialization("Bodybuilding");
        trainer2.setCertification("ISSA Certified");
        trainer2.setExperienceYears(4);
        trainer2.setRating(4.6);

        Trainer trainer3 = new Trainer();
        trainer3.setSpecialization("Functional Training");
        trainer3.setCertification("NASM Certified");
        trainer3.setExperienceYears(5);
        trainer3.setRating(4.7);

        entityManager.persist(trainer1);
        entityManager.persist(trainer2);
        entityManager.persist(trainer3);
        entityManager.flush();
        entityManager.clear();

        // Поиск
        List<Trainer> bodybuildingTrainers = trainerRepository.findBySpecialization("Bodybuilding");

        // Проверка
        assertThat(bodybuildingTrainers).hasSize(2);
        assertThat(bodybuildingTrainers)
                .extracting(Trainer::getCertification)
                .containsExactlyInAnyOrder("ACE Certified", "ISSA Certified");
    }
}