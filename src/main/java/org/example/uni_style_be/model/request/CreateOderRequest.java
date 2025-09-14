package org.example.uni_style_be.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.uni_style_be.enums.PaymentMethod;

@Getter
@Setter
public class CreateOderRequest {
    Long couponId;

    @NotBlank(message = "Tên người nhận không được để trống")
    @Size(max = 50, message = "Tên người nhận không được vượt quá {max} kí tự")
    String fullName;

    @NotBlank(message = "Địa chỉ không được để trống")
    String shippingAddress;

    @Pattern(regexp = "^(\\+84|84|0)?([35789])([0-9]{8})$|^(\\+84|84|0)?(2)([0-9]{8,9})$", message = "Số điện thoại không đúng định dạng")
    @Size(max = 15, message = "Số điện thoại không được vượt quá {max} kí tự")
    String phoneNumber;

    @NotNull(message = "Yêu cầu chon phương thức thanh toán")
    PaymentMethod paymentMethod;

    @Size(max = 255)
    String returnUrl;

    @Size(max = 255)
    String cancelUrl;

    String note;
}
