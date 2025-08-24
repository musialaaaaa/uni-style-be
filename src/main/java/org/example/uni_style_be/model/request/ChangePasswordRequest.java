package org.example.uni_style_be.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    String password;

    @NotBlank(message = "Vui lòng nhập xác nhận mật khẩu")
    String confirmPassword;

}
