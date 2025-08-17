package org.example.uni_style_be.mapper;

import org.example.uni_style_be.entities.Order;
import org.example.uni_style_be.model.response.OrderFilterResponse;
import org.example.uni_style_be.model.response.OrderResponse;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Mapper
@Primary
public interface OrderMapper {

    OrderResponse toOrderResponse(Order order);

    OrderFilterResponse toOrderFilterResponse(Order order);

    List<OrderFilterResponse> toOrderFilterResponse(List<Order> order);
}
