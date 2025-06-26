package org.example.uni_style_be.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.model.request.OrderRequest;
import org.example.uni_style_be.model.request.ShoppingCartRequest;
import org.example.uni_style_be.model.response.OrderResponse;
import org.example.uni_style_be.model.response.ServiceResponse;
import org.example.uni_style_be.model.response.ShoppingCartResponse;
import org.example.uni_style_be.service.OrderService;
import org.example.uni_style_be.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ShoppingCart")
@RequiredArgsConstructor
@Tag(name = "gio hang")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    @PostMapping
    @Operation(summary = "them moi")
    public ShoppingCartResponse create(@RequestBody @Valid ShoppingCartRequest rq) {
        return shoppingCartService.create(rq);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "delete")
    public void delete(@PathVariable Long id) {
        shoppingCartService.delete(id);
    }
    @PutMapping("/{id}")
    @Operation(summary = "update")
    public ShoppingCartRequest update(
            @PathVariable Long id, @Valid @RequestBody ShoppingCartRequest shoppingCartRequest) throws JsonMappingException {
        return shoppingCartService.update(id, shoppingCartRequest);
    }
}
