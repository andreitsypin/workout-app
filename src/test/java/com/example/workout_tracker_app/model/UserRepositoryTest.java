package com.example.workout_tracker_app.model;

import com.example.workout_tracker_app.repository.UserRepository;
import com.example.workout_tracker_app.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
public class UserRepositoryTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testUserCRUD() {
        // Create
        User user = new User();
        user.setId(1L);
        user.setLogin("login");
        user.setEmail("email@example.com");
        user.setName("Test User");
        user.setBirthdate(LocalDate.of(1990, 1, 1));
        user.setRole("ATHLETE");
        user.setHeight(180);
        user.setWeight(75);
        userService.saveUser(user);


        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User test = userService.findById(1L);
        // Read
        verify(userRepository, times(1)).findById(1L);


        // Update
        test.setName("Updated Name");
        assertEquals("Updated Name", userService.findById(test.getId()).getName());

        // Delete
        doNothing().when(userRepository).deleteById(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        userService.deleteById(1L);
        assertNull(userService.findById(1L));
    }
}
