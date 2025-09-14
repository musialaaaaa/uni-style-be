package org.example.uni_style_be.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartStoreDto {

    @NotNull(message = "Bạn phải chọn sản phẩm")
    Long productDetailId;

    @NotNull(message = "Vui lòng chọn số lượng")
    @Min(value = 1, message = "Vui lòng đặt ít nhất một sản phẩm")
    Integer quantity;

}
