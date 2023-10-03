package br.com.yclone.RpgMaker.controller;

import br.com.yclone.RpgMaker.Model.entity.User;
import br.com.yclone.RpgMaker.repository.UserRepository;
import br.com.yclone.RpgMaker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser_ValidUser() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("sdkjfhsdfskjh!1321321");
        when(userService.save(user)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.saveProduct(user);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userService.getAllUsers()).thenReturn(users);

        // Act
        ResponseEntity<Iterable<User>> response = userController.findAll();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void testGetUser_ValidId() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<Optional<User>> response = userController.getUser(userId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(user), response.getBody());
    }

    @Test
    void testGetUser_InvalidId() {
        // Arrange
        Long userId = 1L;

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Optional<User>> response = userController.getUser(userId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testUpdateUser_ValidUser() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userService.updateUser(userId, user)).thenReturn(ResponseEntity.ok(user));

        // Act
        ResponseEntity<User> response = userController.updateUser(userId, user);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    // Add more test cases for edge cases and additional scenarios as needed
}
