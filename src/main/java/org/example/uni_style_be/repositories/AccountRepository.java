package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Account;
import org.example.uni_style_be.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.Instant;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Optional<Account> findByUsername(String username);

    boolean existsByUsernameIgnoreCase(String username);

    long countByTypeAndCreatedAtBetween(AccountType type, Instant createdAtStart, Instant createdAtEnd);


}
