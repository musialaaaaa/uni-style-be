package org.example.uni_style_be.properties;

import lombok.experimental.UtilityClass;
import org.example.uni_style_be.config.ApplicationContextProvider;

@UtilityClass
public class UtilProperties {
    public static JwtProperties jwt() {
        return ApplicationContextProvider.getApplicationContext().getBean(JwtProperties.class);
    }

    public static PayOSProperties payOS() {
        return ApplicationContextProvider.getApplicationContext().getBean(PayOSProperties.class);
    }
}
