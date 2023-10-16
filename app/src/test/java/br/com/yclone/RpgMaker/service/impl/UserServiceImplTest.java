package br.com.yclone.RpgMaker.service.impl;

import br.com.yclone.RpgMaker.Model.entity.User;
import br.com.yclone.RpgMaker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testSaveUser_ValidUser() {
        // PREPARAÇÃO
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("123ABC123abc");


        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // EXECUÇÃO
        User savedUser = userService.save(user);

       // VALIDAÇÃO
        assertNotNull(savedUser);
        assertEquals("test@example.com", savedUser.getEmail());
    }

    @Test
    void testSaveUser_DuplicateEmail() {
        // PREPARAÇÃO
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // EXECUÇÃO &VALIDAÇÃO
        assertThrows(ResponseStatusException.class, () -> userService.save(user));
    }

    @Test
    void testSaveUser_InvalidEmail() {
        // PREPARAÇÃO
        User user = new User();
        user.setEmail("invalid-email");

        // EXECUÇÃO &VALIDAÇÃO
        assertThrows(ResponseStatusException.class, () -> userService.save(user));
    }

    @Test
    void testUpdateUser_ValidUser() {
        // PREPARAÇÃO
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setEmail("existing@example.com");

        User updatedUser = new User();
        updatedUser.setEmail("updated@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // EXECUÇÃO
        User result = userService.updateUser(userId, updatedUser).getBody();

       // VALIDAÇÃO
        assertNotNull(result);
        assertEquals("updated@example.com", result.getEmail());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        // PREPARAÇÃO
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setEmail("updated@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // EXECUÇÃO
        ResponseEntity<User> response = userService.updateUser(userId, updatedUser);

        // VALIDAÇÃO
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testUpdateUser_NullId() {
        // PREPARAÇÃO
        Long userId = null;
        User updatedUser = new User();
        updatedUser.setEmail("updated@example.com");

        // EXECUÇÃO & VALIDAÇÃO
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(userId, updatedUser));
    }


}
