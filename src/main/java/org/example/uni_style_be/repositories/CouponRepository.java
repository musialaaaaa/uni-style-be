package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Coupon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, String>, JpaSpecificationExecutor<Coupon> {
    Optional<Coupon> findByCode(String code);

    @Query(value = "SELECT CONCAT('CP', LPAD(COUNT(*) + 1, 4, '0')) FROM coupon", nativeQuery = true)
    String getNextSeq();
}
