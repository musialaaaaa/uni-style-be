package org.example.uni_style_be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.model.response.PayOSWebhookResponse;
import org.example.uni_style_be.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.payos.type.Webhook;

@RestController
@RequestMapping("/payment")
@Tag(name = "API Thanh toán")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payOS/webhook")
    @Operation(summary = "Webhook dành cho PayOS")
    public PayOSWebhookResponse payOSWebhook(@RequestBody Webhook webhook) {
        return paymentService.payOSWebhook(webhook);
    }

}
