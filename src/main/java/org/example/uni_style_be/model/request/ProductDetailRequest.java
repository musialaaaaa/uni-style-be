package org.example.uni_style_be.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailRequest {

    @NotBlank(message = "Tên không được để trống")
    String name;

    @NotNull(message = "Số lượng không được để trống")
    Integer quantity;

    @NotNull(message = "Giá không được để trống")
    Double price;

    String description;

    @NotNull(message = "Sản phẩm không được để trống")
    Long productId;


    @NotNull(message = "Chất liệu không được để trống")
    Long materialId;

    @NotNull(message = "Màu không được để trống")
    Long colorId;

    @NotNull(message = "Kích thước không được để trống")
    Long sizeId;

    Set<Long> imageIds;
}
