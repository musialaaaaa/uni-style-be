package org.example.uni_style_be.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse<T> extends ServiceResponse<T> {
    private String error;

    @Builder
    public ErrorResponse(String message, T data, String error) {
        super(message, data);
        this.error = error;
    }

}
