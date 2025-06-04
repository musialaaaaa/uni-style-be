package org.example.uni_style_be.uni_style_backend.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldErrorResponse {

    String objectName;

    String field;

    String message;

}
