package org.example.uni_style_be.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class AddToCartRequest {
    @NotNull(message = "Bạn phải chọn sản phẩm")
    private Long productDetailId;
    @NotNull(message = "Vui lòng chọn số lượng")
    @Min(value = 1,message = "Vui lòng đặt ít nhất một sản phẩm")
    private Integer quantity;

}
