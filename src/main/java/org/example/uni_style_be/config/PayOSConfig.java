package org.example.uni_style_be.config;

import lombok.extern.slf4j.Slf4j;
import org.example.uni_style_be.properties.PayOSProperties;
import org.example.uni_style_be.properties.UtilProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import vn.payos.PayOS;

@Slf4j
@Configuration
public class PayOSConfig {

    @Bean
    public PayOS payOS() {
        PayOSProperties payOSProperties = UtilProperties.payOS();
        String clientId = payOSProperties.getClientId();
        String apiKey = payOSProperties.getApiKey();
        String checksumKey = payOSProperties.getChecksumKey();
        String webhookUrl = payOSProperties.getWebhookUrl();
        if (
                !StringUtils.hasText(clientId)
                        || !StringUtils.hasText(apiKey)
                        || !StringUtils.hasText(checksumKey)
                        || !StringUtils.hasText(webhookUrl)
        ) {
            log.error("Cổng thanh toán chưa được cấu hình");
            System.exit(1);
        }
        return new PayOS(clientId, apiKey, checksumKey);
    }
}
