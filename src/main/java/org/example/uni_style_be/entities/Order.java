package org.example.uni_style_be.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.OrderStatus;
import org.springframework.security.core.userdetails.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders") // Đổi tên tránh conflict với từ khóa SQL
public class Order extends BaseEntity {

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    OrderStatus status;

    @Column(name = "shipping_address", columnDefinition = "TEXT", nullable = false)
    String shippingAddress;

    @Column(name = "phone_number", length = 15, nullable = false)
    String phoneNumber;

    //Quan hệ với acccount
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    Account account;

    // Quan hệ với voucher (coupon)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id")
    Coupon voucher;

    // Quan hệ với order_detail
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetails;
}
