package org.example.uni_style_be.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.payos")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayOSProperties {

    String clientId;
    String apiKey;
    String checksumKey;
    String webhookUrl;

}
