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
        // PREPARAÇÃO
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("sdkjfhsdfskjh!1321321");
        when(userService.save(user)).thenReturn(user);

        //  EXECUÇÃO
        ResponseEntity<User> response = userController.saveProduct(user);

        //  VALIDAÇÃO
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetAllUsers() {
        // PREPARAÇÃO
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userService.getAllUsers()).thenReturn(users);

        //  EXECUÇÃO
        ResponseEntity<Iterable<User>> response = userController.findAll();

        //  VALIDAÇÃO
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void testGetUser_ValidId() {
        // PREPARAÇÃO
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        //  EXECUÇÃO
        ResponseEntity<User> response = userController.getUser(userId);

        //  VALIDAÇÃO
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }


    @Test
    void testGetUser_InvalidId() {
        // PREPARAÇÃO
        Long userId = 1L;

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        //  EXECUÇÃO
        ResponseEntity<User> response = userController.getUser(userId);

        //  VALIDAÇÃO
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody()); // Verifique se o corpo da resposta está nulo
    }

    @Test
    void testUpdateUser_ValidUser() {
        // PREPARAÇÃO
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userService.updateUser(userId, user)).thenReturn(ResponseEntity.ok(user));

        //  EXECUÇÃO
        ResponseEntity<User> response = userController.updateUser(userId, user);

        //  VALIDAÇÃO
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }
}
