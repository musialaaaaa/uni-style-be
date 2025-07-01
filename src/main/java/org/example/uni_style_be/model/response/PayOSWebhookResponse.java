package org.example.uni_style_be.model.response;

import lombok.Getter;

@Getter
public class PayOSWebhookResponse {
    private final boolean success;

    public PayOSWebhookResponse() {
        this.success = true;
    }
}
