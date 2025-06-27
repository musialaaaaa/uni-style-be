package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("SELECT COALESCE(MAX(o.id), 0) + 1 FROM Order o")
    Long getNextSeq();
}
