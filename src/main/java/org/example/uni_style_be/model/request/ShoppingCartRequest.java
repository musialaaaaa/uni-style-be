package org.example.uni_style_be.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartRequest {
    @NotNull(message = "không được bỏ trống")
    String name;
    @NotNull(message = "không được bỏ trống")
    String status;

}
