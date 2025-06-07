package org.example.uni_style_be.uni_style_be.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest {
  @NotBlank(message = "Thương hiệu không được để trống")
  private String name;
}
