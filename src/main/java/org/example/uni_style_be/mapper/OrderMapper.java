package org.example.uni_style_be.mapper;

import org.example.uni_style_be.entities.Order;
import org.example.uni_style_be.model.response.OrderResponse;

import java.util.List;
import java.util.stream.Collectors;

public abstract class OrderMapper {

    public static OrderResponse mapToCreateResponse(Order savedOrder) {
        return OrderResponse.builder()
                .id(savedOrder.getId())
                .orderDate(savedOrder.getOrderDate())
                .totalAmount(savedOrder.getTotalAmount())
                .status(savedOrder.getStatus())
                .shippingAddress(savedOrder.getShippingAddress())
                .isDeleted(savedOrder.getIsDeleted())
                .createdBy(savedOrder.getCreatedBy())
                .createdAt(savedOrder.getCreatedAt())
                .updatedBy(savedOrder.getUpdatedBy())
                .updatedAt(savedOrder.getUpdatedAt())
                .build();
    }
    public static List<OrderResponse>mapToCreateResponse(List<Order> orderList) {
        return orderList.stream()
                .map(OrderMapper::mapToCreateResponse)
                .collect(Collectors.toList());
    }

}
