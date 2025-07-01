package org.example.uni_style_be.service.impl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.OrderStatus;
import org.example.uni_style_be.model.response.BaseResponse;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderFilterResponse extends BaseResponse {

    Long code;

    BigDecimal totalAmount;

    OrderStatus status;

    String shippingAddress;

    String phoneNumber;

    String fullName;

}
