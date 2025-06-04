package org.example.uni_style_be.uni_style_backend.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidInputResponse extends ErrorResponse<Void> {

    private Set<FieldErrorResponse> fieldErrors;

    public InvalidInputResponse(String message, String error, Set<FieldErrorResponse> fieldErrors) {
        super(message, null, error);
        this.fieldErrors = fieldErrors;
    }

}
