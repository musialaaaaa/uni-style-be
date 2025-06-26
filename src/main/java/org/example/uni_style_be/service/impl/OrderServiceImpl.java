package org.example.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.example.uni_style_be.entities.Order;
import org.example.uni_style_be.mapper.OrderMapper;
import org.example.uni_style_be.model.filter.OrderParam;
import org.example.uni_style_be.model.request.OrderRequest;
import org.example.uni_style_be.model.response.OrderResponse;
import org.example.uni_style_be.model.response.PageResponse;
import org.example.uni_style_be.repositories.OrderRepository;
import org.example.uni_style_be.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
    private final String PREFIX_CODE = "OD";
    OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    @Override
    public OrderResponse create(OrderRequest rq) {
        // tu sinh ma order

        Long nextValue = orderRepository.getNextSeq();
        String code =  PREFIX_CODE+nextValue;

        Order oder = Order.builder()
                .orderDate(rq.getOrderDate())
                .totalAmount(rq.getTotalAmount())
                .status(rq.getStatus())
                .shippingAddress(rq.getShippingAddress())
                .build();
        // luu vao bang
        Order savedOrder = orderRepository.save(oder);
        return OrderMapper.mapToCreateResponse(savedOrder);

    }

    @Override
    public void delete(Long id) {
        Order Order = orderRepository.findById(id).orElseThrow();
        Order.setIsDeleted(true);
        orderRepository.save(Order);
    }

    @Override
    public OrderRequest  update(Long id, OrderRequest orderRequest) throws JsonMappingException {
       Order order = orderRepository.findById(id).orElseThrow();
        objectMapper.updateValue(order, orderRequest);
        return objectMapper.convertValue(orderRepository.save(order), OrderRequest.class);
    }
    @Override
    public PageResponse<OrderResponse> filter(OrderParam param) {
        Pageable pageable = PageRequest.of(param.getPage()-1,param.getLimit());

        BigDecimal totalAmount = null;
        String status = null;
        String shipping_address= null;
        Boolean isDeleted = null;
        LocalDateTime orderDate = param.getOrderDate();

            if (param.getTotalAmount() != null && param.getTotalAmount().compareTo(BigDecimal.ZERO) > 0) {
                totalAmount = param.getTotalAmount();

            }
            if (StringUtils.isNotBlank(param.getStatus())) {
                status = param.getStatus().trim().toUpperCase();
            }
            if (StringUtils.isNotBlank(param.getShipping_address())) {
                shipping_address = param.getShipping_address().trim().toUpperCase();
            }
             isDeleted = param.getIsDeleted();

        Page<Order> page= orderRepository.filter(orderDate, totalAmount, status, shipping_address, isDeleted, pageable);
        List <Order> orders = page.getContent();
        List<OrderResponse> orderResponses = OrderMapper.mapToCreateResponse(orders);
        return new PageResponse<>(page.getTotalElements(), orderResponses );

    }

}
