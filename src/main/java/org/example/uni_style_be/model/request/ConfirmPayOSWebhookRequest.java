package org.example.uni_style_be.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmPayOSWebhookRequest {

    @NotBlank
    private String webhookUrl;
}
