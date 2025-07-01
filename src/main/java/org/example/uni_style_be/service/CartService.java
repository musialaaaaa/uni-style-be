package org.example.uni_style_be.service;

import org.example.uni_style_be.model.filter.CartParam;
import org.example.uni_style_be.model.request.AddToCartRequest;
import org.example.uni_style_be.model.response.CartResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartService {
    @Transactional
    Void addToCart(AddToCartRequest rq);

    @Transactional
    void updateQuantity(AddToCartRequest rq);

    @Transactional
    void deleteFromCart(Long productDetailId);

    List<CartResponse> getCartItems();
}
