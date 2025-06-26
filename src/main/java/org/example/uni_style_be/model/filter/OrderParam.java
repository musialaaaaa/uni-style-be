package org.example.uni_style_be.model.filter;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE )
public class OrderParam {
    Integer page;

    Integer limit;

    LocalDateTime orderDate;

    BigDecimal totalAmount;

    String status;

    String shipping_address;

    Boolean isDeleted;
}
