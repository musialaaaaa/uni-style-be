package org.example.uni_style_be.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequest {
    @NotBlank(message = "So luong không được để trống")
    Integer quantity;

    @NotBlank(message = "Gia không được để trống")
    BigDecimal price_at_time;
}
