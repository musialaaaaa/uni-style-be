package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail,Long> {
    Optional<CartDetail> findByCartIdAndProductDetail_Id(Long cartId, Long productDetailId);
    List<CartDetail> findByCartId(Long cartId);

}
