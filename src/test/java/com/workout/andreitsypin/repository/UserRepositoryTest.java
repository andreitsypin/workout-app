package com.workout.andreitsypin.repository;

import com.workout.andreitsypin.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.workout.andreitsypin.model.User.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    // ('user2', 'user2@example.com', 'Петр Петров', '$2a$10$xJwL5vG6J/dQ1X8Q5WQZ.eqj9U0tLd9JWd9XJv6K3Jd9XJv6K3Jd9', 'USER', '1985-05-20', 175, 80, NOW()),
    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenSaveUser_thenCanFindByEmail() {
        User user = new User();

        user.setLogin("testuser"); // Установите обязательное поле login
        user.setEmail("test@example.com");
        user.setName("Андрей Андреевич");
        user.setPassword("qwert");
        user.setRole(USER);
        LocalDate date = LocalDate.now();
        user.setBirthDate(date);
        user.setHeight(190);
        user.setWeight(90);
        LocalDateTime date2 = LocalDateTime.now();
        user.setCreatedAt(date2);

        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("test@example.com");
        assertThat(found).isPresent();
    }

    @Test
    @Transactional
    public void whenUpdateUser_thenDataChanged() {
        // 1. Создаем и сохраняем пользователя
        User user = new User();
        user.setLogin("testuser");
        user.setEmail("test@example.com");
        user.setName("Андрей Андреевич");
        user.setPassword("qwert");
        user.setRole(USER);
        user.setBirthDate(LocalDate.now());
        user.setHeight(190);
        user.setWeight(90);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        // 2. Обновляем данные
        User userToUpdate = userRepository.findByLogin("testuser").orElseThrow();
        userToUpdate.setName("Новое Имя");
        userToUpdate.setHeight(185);
        userRepository.save(userToUpdate); // Важно: save() работает и для обновления

        // 3. Проверяем обновленные данные
        User updatedUser = userRepository.findByLogin("testuser").orElseThrow();
        assertThat(updatedUser.getName()).isEqualTo("Новое Имя");
        assertThat(updatedUser.getHeight()).isEqualTo(185);
    }

    @Test
    @Transactional
    public void whenDeleteUser_thenNotFound() {
        // 1. Создаем и сохраняем пользователя
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

        userRepository.save(user);

        // 2. Удаляем пользователя
        userRepository.delete(user);

        // 3. Проверяем, что пользователя больше нет
        Optional<User> deletedUser = userRepository.findByLogin("todelete");
        assertThat(deletedUser).isEmpty();
    }

    @Test
    public void whenFindByBirthDateBetween_thenReturnUsers() {
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

        userRepository.save(user);

        List<User> users = userRepository.findByBirthDateBetween(
                LocalDate.of(1980, 1, 1),
                LocalDate.of(2300, 1, 1)
        );
        assertThat(users).hasSize(1);
    }
}