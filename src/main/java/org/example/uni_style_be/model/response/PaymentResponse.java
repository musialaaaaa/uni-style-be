package org.example.uni_style_be.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.PaymentMethod;
import org.example.uni_style_be.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {

    PaymentMethod paymentMethod;

    BigDecimal paymentAmount;

    LocalDateTime paymentTime;

    String transactionCode;

    String note;

    PaymentStatus status;

    String checkoutUrl;

    String qrCode;
}
