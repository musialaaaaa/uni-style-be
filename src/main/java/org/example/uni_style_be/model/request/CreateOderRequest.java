package org.example.uni_style_be.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.example.uni_style_be.enums.PaymentMethod;

@Getter
@Setter
public class CreateOderRequest {
    Long voucherId;

    @NotBlank(message = "Địa chỉ không được để trống")
    String shippingAddress;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "/(84|0[3|5|7|8|9])+([0-9]{8})\\b/g",message = "Số điện thoại không đúng định dạng")
    String phoneNumber;

    @NotNull(message = "Yêu cầu chon phương thức thanh toán")
    PaymentMethod paymentMethod;
}
