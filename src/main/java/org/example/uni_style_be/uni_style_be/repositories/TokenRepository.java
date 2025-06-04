package org.example.uni_style_be.uni_style_backend.repositories;

import org.example.uni_style_be.uni_style_backend.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    @Transactional
    @Modifying
    @Query("update Token t set t.expired = ?1 where t.token = ?2")
    void updateExpiredByToken(boolean expired, String token);
}
