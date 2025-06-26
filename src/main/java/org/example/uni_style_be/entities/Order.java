package org.example.uni_style_be.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order  extends BaseEntity{

@Column(nullable = false)
LocalDateTime orderDate;
@Column(nullable = false)
    BigDecimal totalAmount;
@Column(nullable = false)
    String status;
@Column(nullable = false)
    String shippingAddress;
@Column(nullable = false, name = "is_deleted")
    Boolean isDeleted =Boolean.FALSE;

}