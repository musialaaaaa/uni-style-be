package org.example.uni_style_be.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse extends BaseResponse {

    LocalDateTime orderDate;

    BigDecimal totalAmount;

    String status;

    String shippingAddress;

    Boolean isDeleted;
}
