package org.example.uni_style_be.uni_style_be.properties;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.uni_style_be.uni_style_be.config.ApplicationContextProvider;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UtilProperties {
    public static JwtProperties jwt() {
        return ApplicationContextProvider.getApplicationContext().getBean(JwtProperties.class);
    }
}
