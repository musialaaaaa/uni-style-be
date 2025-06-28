package org.example.uni_style_be.repositories;

import org.example.uni_style_be.entities.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    List<CartDetail> findByCartId(Long cartId); // Tìm CartDetail theo cartId
    // Các phương thức tùy chỉnh có thể được thêm vào đây nếu cần
    // Ví dụ: tìm kiếm theo tài khoản, sản phẩm, v.v.
}
