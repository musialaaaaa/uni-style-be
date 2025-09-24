package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.ProductDetail;
import org.example.uni_style_be.enums.ProductDetailStatus;
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

    boolean existsByColor_Id(Long id);

    boolean existsByMaterial_Id(Long id);

    boolean existsBySize_Id(Long id);

    boolean existsByProduct_IdAndStatusAndIdNot(Long productId, ProductDetailStatus status, Long productDetailId);

    boolean existsByProduct_IdAndStatus(Long productId, ProductDetailStatus status);

}
