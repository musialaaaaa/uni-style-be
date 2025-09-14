package org.example.uni_style_be.service;

import org.example.uni_style_be.model.filter.OrderParam;
import org.example.uni_style_be.model.request.CreateOderRequest;
import org.example.uni_style_be.model.request.OrderStoreRequest;
import org.example.uni_style_be.model.response.CreateOrderResponse;
import org.example.uni_style_be.model.response.OrderFilterResponse;
import org.example.uni_style_be.model.response.OrderResponse;
import org.example.uni_style_be.model.response.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    CreateOrderResponse createOrder(CreateOderRequest request);

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    CreateOrderResponse orderAtStore(OrderStoreRequest request);

    @Transactional
    Void cancelOrder(Long orderCode);

    OrderResponse detail(Long id);

    PageResponse<OrderFilterResponse> filter(OrderParam param, Pageable pageable);

    PageResponse<OrderFilterResponse> filterMyOrder(OrderParam param, Pageable pageable);
}
