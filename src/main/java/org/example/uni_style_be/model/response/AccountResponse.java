package org.example.uni_style_be.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse extends BaseResponse {

    String username;

    String fullName;

    String email;
}
