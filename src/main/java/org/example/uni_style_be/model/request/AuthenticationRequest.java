package org.example.uni_style_be.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {

    @NotBlank
    String username;

    @NotBlank
    String password;

    public String getUsername() {
        return username.toUpperCase();
    }

}
