package org.example.uni_style_be.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.model.filter.OrderParam;
import org.example.uni_style_be.model.request.OrderRequest;
import org.example.uni_style_be.model.response.OrderResponse;
import org.example.uni_style_be.model.response.PageResponse;
import org.example.uni_style_be.model.response.ServiceResponse;
import org.example.uni_style_be.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "Order")
public class OrderController {

}
