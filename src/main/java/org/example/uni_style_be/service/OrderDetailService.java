package org.example.uni_style_be.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.example.uni_style_be.entities.OrderDetail;
import org.example.uni_style_be.model.filter.OrderDetailParam;
import org.example.uni_style_be.model.request.OrderDetailRequest;
import org.example.uni_style_be.model.response.OrderDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderDetailService {
    OrderDetailResponse createOrderDetail(OrderDetailRequest orderDetailRequest);

    OrderDetailResponse updateOrderDetail(Long id, OrderDetailRequest orderDetailRequest)throws JsonMappingException;

    void delete(Long id);

    OrderDetail findById(Long id);

    Page<OrderDetailResponse> filter(OrderDetailParam Param, Pageable pageable);
}
