package org.example.uni_style_be.model.filter;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailParam {

    private Integer quantity;

    private BigDecimal price_at_time;
}
