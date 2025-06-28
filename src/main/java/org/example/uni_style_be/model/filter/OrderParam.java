package org.example.uni_style_be.model.filter;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderParam {
    private LocalDateTime orderDate;

    private BigDecimal totalAmount;

    private String status;

    private String shippingAddress;
}
