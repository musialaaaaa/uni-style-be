package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Product;
import org.example.uni_style_be.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT COALESCE(MAX(p.id), 0) + 1 FROM Product p")
    Long getNextSeq();

    boolean existsByCategory_Id(Long id);

    @Transactional
    @Modifying
    @Query("update Product p set p.status = ?2 where p.id = ?1")
    void updateStatusById(Long productId, ProductStatus status);
}
