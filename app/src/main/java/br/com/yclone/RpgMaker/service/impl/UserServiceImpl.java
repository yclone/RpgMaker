package br.com.yclone.RpgMaker.service.impl;

import br.com.yclone.RpgMaker.Model.entity.User;
import br.com.yclone.RpgMaker.repository.UserRepository;
import br.com.yclone.RpgMaker.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User any) {
        User user = userRepository.save(any);
        return user;
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
