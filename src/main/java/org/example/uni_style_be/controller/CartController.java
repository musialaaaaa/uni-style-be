package org.example.uni_style_be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.model.filter.CartParam;
import org.example.uni_style_be.model.request.AddToCartRequest;
import org.example.uni_style_be.model.request.DeleteCartRequest;
import org.example.uni_style_be.model.response.AddToCartResponse;
import org.example.uni_style_be.model.response.ServiceResponse;
import org.example.uni_style_be.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Giỏ Hàng")
public class CartController {
   private final CartService cartService;
   @PostMapping
   @Operation(summary = "thêm mới")
   public ServiceResponse<String>create(@RequestBody @Valid AddToCartRequest rq) {
      cartService.addToCart(rq);
      return ServiceResponse.ok("them thanh cong");
   }
   @PutMapping
   @Operation(summary = "Cập nhật")
   public ServiceResponse<String> updateQuantity(@RequestBody @Valid AddToCartRequest rq) {
      cartService.updateQuantity(rq);
      return ServiceResponse.ok("Cập nhật thành công");
   }
   @DeleteMapping
   @Operation(summary = "Xoá")
   public ServiceResponse<String> deleteFromCart(@RequestBody @Valid DeleteCartRequest rq) {
      cartService.deleteFromCart(rq.getProductDetailId());
      return ServiceResponse.ok("Xóa sản phẩm khỏi giỏ hàng thành công");
   }
   @GetMapping
   @Operation(summary = "Xem giỏ hàng")
   public ServiceResponse<List<CartParam>> getCartItems() {
      return ServiceResponse.ok(cartService.getCartItems());
   }




}
