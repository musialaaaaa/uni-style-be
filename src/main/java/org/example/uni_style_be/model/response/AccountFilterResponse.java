package org.example.uni_style_be.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.AccountType;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountFilterResponse {

    Long id;

    String username;

    String fullName;

    String email;

    String phone;

    AccountType type;
}
