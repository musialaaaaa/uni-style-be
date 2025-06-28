package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartReposotory extends JpaRepository<Cart, Long> {
    Cart findByAccountId(Long accountId); // Thêm phương thức tùy chỉnh
    @Query("SELECT COALESCE(MAX(s.id), 0) + 1 FROM Cart s")
    Long getNextSeq();
}

