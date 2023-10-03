package br.com.yclone.RpgMaker.controller;

import br.com.yclone.RpgMaker.Model.dto.UserDTO;
import br.com.yclone.RpgMaker.Model.entity.User;
import br.com.yclone.RpgMaker.repository.UserRepository;
import br.com.yclone.RpgMaker.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.experimental.NonFinal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<User> saveProduct(@RequestBody User user) {
        if (!isPasswordSecure(user.getPassword())) {
            throw new ResponseStatusException(BAD_REQUEST, "A senha não atende aos critérios de segurança.");
        }
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    private boolean isPasswordSecure(String password) {
        // Adicione sua lógica de validação de senha aqui, por exemplo, verificar comprimento e caracteres.
        return password.length() >= 8 && password.matches(".*[a-z].*") && password.matches(".*\\d.*");
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> findAll() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()){
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") Long userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }


}
