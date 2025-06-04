package org.example.uni_style_be.uni_style_be.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse<T> {
    private String message;
    private T data;

    public static <T> ServiceResponse<T> ok(T data) {
        return new ServiceResponse<>("OK", data);
    }

}
