package org.example.uni_style_be.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderResponse {

    Long id;

    Long code;

    BigDecimal totalAmount;

    String checkoutUrl;

    String qrCode;


}
