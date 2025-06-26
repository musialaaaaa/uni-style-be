package org.example.uni_style_be.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotNull(message = "không được bỏ trống")
    LocalDateTime orderDate;
    @NotNull (message = "không được bỏ trống")
    BigDecimal totalAmount;
    @NotBlank(message = "không được bỏ trống")
    String status;
    @NotBlank(message = "không được bỏ trống")
    String shippingAddress;
}
