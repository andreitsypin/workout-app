package com.workout.andreitsypin.service;

import com.workout.andreitsypin.model.User;
import com.workout.andreitsypin.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    void findAll_shouldReturnAllUsers() {
        // Arrange
        User user1 = new User();
        User user2 = new User();
        when(repository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<User> result = service.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnUser_whenExists() {
        // Arrange
        User user = new User();
        user.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = service.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void save_shouldReturnSavedUser() {
        // Arrange
        User user = new User();
        when(repository.save(user)).thenReturn(user);

        // Act
        User result = service.save(user);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).save(user);
    }
}