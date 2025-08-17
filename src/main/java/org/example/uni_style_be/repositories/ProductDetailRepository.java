package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository
        extends JpaRepository<ProductDetail, Long>,
        JpaSpecificationExecutor<ProductDetail> {

    @Query("SELECT COALESCE(MAX(m.id), 0) + 1 FROM ProductDetail m")
    Long getNextSeq();

}
