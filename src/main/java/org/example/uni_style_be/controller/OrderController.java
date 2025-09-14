package org.example.uni_style_be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.model.filter.OrderParam;
import org.example.uni_style_be.model.request.CreateOderRequest;
import org.example.uni_style_be.model.request.OrderStoreRequest;
import org.example.uni_style_be.model.response.*;
import org.example.uni_style_be.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "API Đơn hàng")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Đặt hàng")
    public ServiceResponse<CreateOrderResponse> create(@Valid @RequestBody CreateOderRequest request) {
        return ServiceResponse.ok(orderService.createOrder(request));
    }

    @PostMapping("/cancel/order-code/{orderCode}")
    @Operation(summary = "Hủy đơn hàng")
    public ServiceResponse<Void> cancel(@PathVariable Long orderCode) {
        return ServiceResponse.ok(orderService.cancelOrder(orderCode));
    }

    @GetMapping("{id}")
    @Operation(summary = "Chi tiết đơn hàng")
    public ServiceResponse<OrderResponse> detail(@PathVariable Long id) {
        return ServiceResponse.ok(orderService.detail(id));
    }

    @GetMapping
    @Operation(summary = "Danh sách đơn hàng")
    public ServiceResponse<PageResponse<OrderFilterResponse>> filter(OrderParam param, Pageable pageable) {
        return ServiceResponse.ok(orderService.filter(param, pageable));
    }

    @GetMapping("/my-orders")
    @Operation(summary = "Danh sách đơn hàng của người dùng hiện tại")
    public ServiceResponse<PageResponse<OrderFilterResponse>> filterMyOrder(OrderParam param, Pageable pageable) {
        return ServiceResponse.ok(orderService.filterMyOrder(param, pageable));
    }

    @PostMapping("at-store")
    @Operation(summary = "Đặt hàng tại quầy")
    public ServiceResponse<CreateOrderResponse> orderAtStore(@RequestBody @Valid OrderStoreRequest request) {
        return ServiceResponse.ok(orderService.orderAtStore(request));
    }
}
