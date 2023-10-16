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
    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(BAD_REQUEST, "Cliente já cadastrado");
        }

        if (!isValidEmail(user.getEmail())) {
            throw new ResponseStatusException(BAD_REQUEST, "Email inválido");
        }

        if (!PasswordUtil.isSecurePassword(user.getPassword())) {
            throw new ResponseStatusException(BAD_REQUEST, "Senha insegura");
        }

        User savedUser = userRepository.save(user);
        return savedUser;
    }


    private boolean isValidEmail(String email) {
        // Utilize uma expressão regular para validar o formato do email
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex);
    }

    public class PasswordUtil {
        public static boolean isSecurePassword(String password) {
            // Implemente aqui a lógica de verificação da força da senha
            // Por exemplo, verificando comprimento, caracteres especiais, letras maiúsculas, etc.
            return password.length() >= 8 && password.matches(".*[a-z].*") && password.matches(".*\\d.*");
        }
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
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User existingUser = optionalUser.get();
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setNome(user.getNome());
        existingUser.setSobrenome(user.getSobrenome());
        User savedEntity = userRepository.save(existingUser);
        return ResponseEntity.ok(savedEntity);
    }
}
