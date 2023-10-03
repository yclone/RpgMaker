package br.com.yclone.RpgMaker.repository;

import br.com.yclone.RpgMaker.Model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
