package org.example.uni_style_be.uni_style_backend.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.security.jwt")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtProperties {

    String secretKey;

    long tokenExpiration;

    long refreshTokenExpiration;

}
