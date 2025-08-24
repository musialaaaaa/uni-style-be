package org.example.uni_style_be.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.ProductStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {

    @NotBlank(message = "Tên không được để trống")
    String name;

    String description;

    @NotNull(message = "Danh mục không được để trống")
    Long categoryId;

    ProductStatus status;
}
