package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT COALESCE(MAX(d.id), 0) + 1 FROM Order d")
    Long getNextSeq();

    @Query("""
           SELECT d FROM Order d
           WHERE (:orderDate IS NULL OR d.orderDate = :orderDate)
           AND (:totalAmount IS NULL OR d.totalAmount = :totalAmount)
           AND (:status IS NULL OR d.status = :status)
           AND (:shippingAddress IS NULL OR d.shippingAddress = :shippingAddress)
           AND (:isDeleted IS NULL OR d.isDeleted = :isDeleted)
           """)
    Page<Order> filter(
            LocalDateTime orderDate,
            BigDecimal totalAmount,
            String status,
            String shippingAddress,
            Boolean isDeleted,
            Pageable pageable
    );

}
