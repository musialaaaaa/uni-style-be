package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Order;
import org.example.uni_style_be.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("SELECT COALESCE(MAX(o.id), 0) + 1 FROM Order o")
    Long getNextSeq();

    Optional<Order> findByCode(Long code);

    List<Order> findByStatusAndExpiredAtBefore(OrderStatus status, LocalDateTime expiredAt);
}
