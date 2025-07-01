package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long>{
    Optional<Cart> findByAccountId(Long accountId);

}
