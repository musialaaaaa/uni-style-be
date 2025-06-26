package org.example.uni_style_be.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.example.uni_style_be.model.filter.OrderParam;
import org.example.uni_style_be.model.request.OrderRequest;
import org.example.uni_style_be.model.response.OrderResponse;
import org.example.uni_style_be.model.response.PageResponse;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {
    OrderResponse create(OrderRequest rq);

    @Transactional
    void delete(Long id);

    @Transactional
    OrderRequest  update(Long id, OrderRequest orderRequest) throws JsonMappingException;

    PageResponse<OrderResponse> filter(OrderParam param);
}
