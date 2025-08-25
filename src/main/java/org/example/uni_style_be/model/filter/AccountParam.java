package org.example.uni_style_be.model.filter;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.enums.AccountType;
import org.example.uni_style_be.utils.SqlUtils;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountParam {
    String keyword;

    AccountType type;

    public String getKeyword() {
        return SqlUtils.encodeKeyword(keyword);
    }
}
