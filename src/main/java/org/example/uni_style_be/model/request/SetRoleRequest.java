package org.example.uni_style_be.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SetRoleRequest {

    @NotNull(message = "Vui lòng chọn quyền cần gán")
    @NotEmpty(message = "Vui lòng chọn quyền cần gán")
    Set<Long> roleIds;

    @NotNull(message = "Vui lòng chọn tài khoản")
    Long accountId;

}
