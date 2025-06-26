package org.example.uni_style_be.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotNull(message = "không được bỏ trống")
    LocalDateTime orderDate;
    @NotNull (message = "không được bỏ trống")
    BigDecimal total_amount;
    @NotBlank(message = "không được bỏ trống")
    String status;
    @NotBlank(message = "không được bỏ trống")
    String shipping_address;
}
