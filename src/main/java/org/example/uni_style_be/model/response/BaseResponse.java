package org.example.uni_style_be.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

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
