package org.example.uni_style_be.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.OrderStatus;
import org.example.uni_style_be.model.OrderDetailDto;

import java.math.BigDecimal;
import java.util.List;

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

    CouponResponse coupon;

    List<OrderDetailDto> orderDetails;

}
