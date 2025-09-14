package org.example.uni_style_be.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.PaymentMethod;
import org.example.uni_style_be.model.CartStoreDto;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderStoreRequest {

    @NotEmpty(message = "Vui lòng chọn sản phẩm")
    @Valid
    List<CartStoreDto> cart;

    Long couponId;

    @NotNull(message = "Yêu cầu chon phương thức thanh toán")
    PaymentMethod paymentMethod;

    @Size(max = 255)
    String returnUrl;

    @Size(max = 255)
    String cancelUrl;

    String note;
}
