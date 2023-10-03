package br.com.yclone.RpgMaker.service.impl;

import br.com.yclone.RpgMaker.Model.entity.User;
import br.com.yclone.RpgMaker.repository.UserRepository;
import br.com.yclone.RpgMaker.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User any) {
        if(userRepository.existsByEmail(any.getEmail())){
            throw new ResponseStatusException(BAD_REQUEST, "cliente já cadastrado");
        }

        // Validar o formato do email
        if (!isValidEmail(any.getEmail())) {
            throw new ResponseStatusException(BAD_REQUEST, "Email inválido");
        }

        User user = userRepository.save(any);
        return user;
    }

    private boolean isValidEmail(String email) {
        // Utilize uma expressão regular para validar o formato do email
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public ResponseEntity<User> updateUser(Long userId, User user) {
        if (userId == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(userId)));
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setNome(user.getNome());
        existingUser.setSobrenome(user.getSobrenome());
        User savedEntity = userRepository.save(existingUser);
        return ResponseEntity.ok(savedEntity);
    }


}
