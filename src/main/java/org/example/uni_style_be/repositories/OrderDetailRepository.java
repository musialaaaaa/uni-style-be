package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, JpaSpecificationExecutor<OrderDetail> {
    @Query("SELECT COALESCE(MAX(od.id), 0) + 1 FROM OrderDetail od")
    Long getNextSeq();
}
