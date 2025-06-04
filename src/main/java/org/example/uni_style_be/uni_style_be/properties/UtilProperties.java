package org.example.uni_style_be.uni_style_backend.properties;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.uni_style_be.uni_style_backend.config.ApplicationContextProvider;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UtilProperties {
    public static JwtProperties jwt() {
        return ApplicationContextProvider.getApplicationContext().getBean(JwtProperties.class);
    }
}
