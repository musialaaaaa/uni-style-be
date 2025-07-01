package org.example.uni_style_be.service;

import org.example.uni_style_be.model.response.PayOSWebhookResponse;
import vn.payos.type.Webhook;

public interface PaymentService {
    PayOSWebhookResponse payOSWebhook(Webhook webhook);
}
