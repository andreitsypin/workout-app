package com.workout.andreitsypin.controller;

import com.workout.andreitsypin.model.User;
import com.workout.andreitsypin.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    @Test
    void getAll_shouldReturnAllUsers() {
        // Arrange
        User user1 = new User();
        User user2 = new User();
        when(service.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<User> result = controller.getAll();

        // Assert
        assertEquals(2, result.size());
        verify(service, times(1)).findAll();
    }

    @Test
    void create_shouldSaveAndReturnUser() {
        // Arrange
        User user = new User();
        when(service.save(user)).thenReturn(user);

        // Act
        User result = controller.create(user);

        // Assert
        assertNotNull(result);
        verify(service, times(1)).save(user);
    }

    @Test
    void delete_shouldReturnNoContent_whenExists() {
        // Arrange
        when(service.findById(1L)).thenReturn(Optional.of(new User()));

        // Act
        ResponseEntity<Void> response = controller.delete(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(1L);
    }
}