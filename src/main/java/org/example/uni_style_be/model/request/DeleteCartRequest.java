package org.example.uni_style_be.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteCartRequest {
    @NotNull(message = "Bạn phải chọn sản phẩm")
    private Long productDetailId;
}
