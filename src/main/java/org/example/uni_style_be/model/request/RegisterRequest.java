package org.example.uni_style_be.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {

    @NotBlank(message = "Vui lòng nhập tên đăng nhập")
    @Size(min = 5, max = 50, message = "Tên đăng nhập tối thiểu {min} kí tự và tối đa {max} kí tự")
    String username;

    @NotBlank(message = "Vui lòng nhập họ và tên")
    @Size(min = 1, max = 50, message = "Họ và tên tối thiểu {min} kí tự và tối đa {max} kí tự")
    String fullName;

//    @NotBlank(message = "Vui lòng nhập email")
    @Email(message = "Email không hợp lệ")
    String email;

//    @NotBlank(message = "Vui lòng nhập số điện thoại")
    @Pattern(
            regexp = "^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$",
            message = "Số điện thoại không hợp lệ"
    )
    String phone;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    String password;

    @NotBlank(message = "Vui lòng nhập xác nhận mật khẩu")
    String confirmPassword;
}
