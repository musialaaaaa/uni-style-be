package org.example.uni_style_be.utils;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

@UtilityClass
public class SqlUtils {
    public static String encodeKeyword(String input) {
        if (StringUtils.hasText(input)) {
            return input.trim().toUpperCase()
                    .replaceAll("%", "\\\\%");
        }
        return null;
    }

}
