package org.example.uni_style_be.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.uni_style_be.entities.Order;
import org.example.uni_style_be.entities.Payment;
import org.example.uni_style_be.enums.InvalidInputError;
import org.example.uni_style_be.enums.OrderStatus;
import org.example.uni_style_be.enums.PaymentStatus;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.model.response.PayOSWebhookResponse;
import org.example.uni_style_be.properties.UtilProperties;
import org.example.uni_style_be.repositories.OrderRepository;
import org.example.uni_style_be.repositories.PaymentRepository;
import org.example.uni_style_be.service.PaymentService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {

    PayOS payOS;
    OrderRepository orderRepository;
    PaymentRepository paymentRepository;

    @Override
    public PayOSWebhookResponse payOSWebhook(Webhook webhook) {
        try {
            WebhookData webhookData = payOS.verifyPaymentWebhookData(webhook);
            Order order = orderRepository.findByCode(webhookData.getOrderCode())
                    .orElseThrow(() -> new ResponseException(InvalidInputError.ORDER_NOT_FOUND));
            if (!OrderStatus.PENDING.equals(order.getStatus())) {
                throw new ResponseException(InvalidInputError.ORDER_STATUS_INVALID);
            }
            Payment payment = paymentRepository.findFirstByOrderAndPaymentLinkId(order, webhookData.getPaymentLinkId())
                    .orElseThrow(() -> new ResponseException(InvalidInputError.PAYMENT_NOT_FOUND));
            payment.setPartnerCode(webhook.getCode());
            if ("00".equals(webhookData.getCode())) {
                payment.setStatus(PaymentStatus.CONFIRMED);
                payment.setTransactionCode(webhookData.getReference());
                LocalDateTime paymentTime = LocalDateTime.parse(
                        webhookData.getTransactionDateTime(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                );
                payment.setPaymentTime(paymentTime);
                payment.setPaymentAmount(BigDecimal.valueOf(webhookData.getAmount()));
            }
            paymentRepository.save(payment);

            List<Payment> payments = paymentRepository.findByOrderAndStatus(order, PaymentStatus.CONFIRMED);
            BigDecimal totalPaymentAmount = BigDecimal.ZERO;
            for (Payment pay : payments) {
                totalPaymentAmount = totalPaymentAmount.add(pay.getPaymentAmount());
            }

            int compareAmount = totalPaymentAmount.compareTo(order.getTotalAmount());
            if (compareAmount >= 0) {
                order.setStatus(OrderStatus.CONFIRMED);
                orderRepository.save(order);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return new PayOSWebhookResponse();
    }

    @EventListener(ApplicationReadyEvent.class)
    private void confirmPayOSWebhook() {
        try {
            String result = payOS.confirmWebhook(UtilProperties.payOS().getWebhookUrl());
            log.info("PayOS Webhook đã cấu hình thành công tại: {}", result);
        } catch (Exception e) {
            log.error("Cấu hình PayOS Webhook thất bại", e);
            System.exit(1);
        }
    }
}
