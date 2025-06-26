package org.example.uni_style_be.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.Order;
import org.example.uni_style_be.model.filter.OrderParam;
import org.example.uni_style_be.model.request.OrderRequest;
import org.example.uni_style_be.model.request.ProductRequest;
import org.example.uni_style_be.model.response.OrderResponse;
import org.example.uni_style_be.model.response.PageResponse;
import org.example.uni_style_be.model.response.ProductResponse;
import org.example.uni_style_be.model.response.ServiceResponse;
import org.example.uni_style_be.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/Order")
@RequiredArgsConstructor
@Tag(name = "Order")
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @Operation(summary = "them moi")
    public ServiceResponse<OrderResponse> create(@RequestBody @Valid OrderRequest rq) {
        return ServiceResponse.ok(orderService.create(rq));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "delete")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
    @PutMapping("/{id}")
    @Operation(summary = "update")
    public OrderRequest update(
            @PathVariable Long id, @Valid @RequestBody OrderRequest orderRequest) throws JsonMappingException {
        return orderService.update(id, orderRequest);
    }
    @GetMapping
    @Operation(summary = "Danh sách")
    public ServiceResponse<PageResponse<OrderResponse>> filter(
            @RequestParam(value = "orderDate", required = false) LocalDateTime orderDate,
            @RequestParam(value = "totalAmount", required = false) BigDecimal totalAmount,
            @RequestParam(value = "status", required = false)     String status,
            @RequestParam(value = "shipping_address", required = false)  String shipping_address,
            @RequestParam(value = "isDeleted", required = false)  Boolean isDeleted,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
    ) {
        OrderParam param = OrderParam.builder()
                .orderDate(orderDate)
                .totalAmount(totalAmount)
                .status(status)
                .shipping_address(shipping_address)
                .page(page)
                .isDeleted(isDeleted)
                .limit(limit)
                .build();

        return ServiceResponse.ok(orderService.filter(param));
    }
}
