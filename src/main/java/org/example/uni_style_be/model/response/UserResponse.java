package org.example.uni_style_be.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse extends BaseResponse {

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String status;

    private Boolean isDeleted;
}
