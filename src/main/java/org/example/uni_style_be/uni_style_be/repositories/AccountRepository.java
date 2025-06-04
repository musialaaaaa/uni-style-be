package org.example.uni_style_be.uni_style_backend.repositories;

import org.example.uni_style_be.uni_style_backend.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);

    boolean existsByUsernameIgnoreCase(String username);
}
