package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>, JpaSpecificationExecutor<Coupon> {
    Optional<Coupon> findByCode(String code);

    @Query(value = "SELECT CONCAT('CP', LPAD(COUNT(*) + 1, 4, '0')) FROM coupon", nativeQuery = true)
    String getNextSeq();

    Optional<Coupon> findFirstByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCaseAndIdNot(String code, Long id);


}
