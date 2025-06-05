package org.example.uni_style_be.uni_style_be.model.response;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseResponse {

  private Long id;

  private String createdBy;

  private Instant createdAt;

  private String updatedBy;

  private Instant updatedAt;
}
