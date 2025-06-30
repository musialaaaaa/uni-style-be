package org.example.uni_style_be.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.entities.Cart;
import org.example.uni_style_be.entities.CartDetail;
import org.example.uni_style_be.entities.ProductDetail;
import org.example.uni_style_be.enums.InvalidInputError;
import org.example.uni_style_be.enums.UnauthorizedError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.model.filter.CartParam;
import org.example.uni_style_be.model.request.AddToCartRequest;
import org.example.uni_style_be.repositories.CartDetailRepository;
import org.example.uni_style_be.repositories.CartRepository;
import org.example.uni_style_be.repositories.ProductDetailRepository;
import org.example.uni_style_be.service.CartService;
import org.example.uni_style_be.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    CartDetailRepository cartDetailRepository;
    ProductDetailRepository productDetailRepository;
    @Transactional
    @Override
    public Void addToCart(AddToCartRequest rq) {
        // lay ra productDetail
        ProductDetail productDetail1 = productDetailRepository.findById(rq.getProductDetailId())
                .orElseThrow(()->new ResponseException(InvalidInputError.PRODUCT_DETAIL_NOT_EXIST));

       // kiem tra xem san pham co con ton tai khong
        Optional<ProductDetail> optionalProductDetail= productDetailRepository.findById(rq.getProductDetailId());
        if (optionalProductDetail.isEmpty()) {
            throw new ResponseException(InvalidInputError.PRODUCT_DETAIL_NOT_EXIST);
        }
        // kiem tra xem soluong cua khach co vuot qua sl ton khong
        ProductDetail productDetail = optionalProductDetail.get();
        if (rq.getQuantity()>productDetail.getQuantity()) {
            throw new ResponseException(InvalidInputError.QUANTITY_IS_NOT_ENOUGH,productDetail.getQuantity());
        }
        // b1 lay duoc acoun id
        Optional<Long> accountIdOptional = SecurityUtils.getCurrentAccountId();
        if (accountIdOptional.isEmpty()) {
            throw new ResponseException(UnauthorizedError.UNAUTHORIZED);
        }
        Long accountId = accountIdOptional.get();
    // b2 lay gio hang cua acoun id neu khong co thi tao gio hang
        Optional<Cart> optionalCart= cartRepository.findByAccountId(accountId);
        Cart cart;
        if (optionalCart.isEmpty()) {
            Cart cartToSave = Cart.builder()
                    .accountId(accountId)
                    .build();
           cart = cartRepository.save(cartToSave);

        }else{
            cart = optionalCart.get();
        }
        // kiem tra xem san pham co trong gio hang chua
        Optional<CartDetail>optionalCartDetail=cartDetailRepository
                .findByCartIdAndProductDetail_Id(cart.getId(),productDetail1.getId());
        if (optionalCartDetail.isPresent()) {
            // sp da co trong gio hang tang sl
            CartDetail cartDetail = optionalCartDetail.get();
            int updatedQuantity = cartDetail.getQuantity() + rq.getQuantity();
        if (updatedQuantity > productDetail.getQuantity()) {
            throw new ResponseException(InvalidInputError.QUANTITY_IS_NOT_ENOUGH,productDetail.getQuantity());
        }
            cartDetail.setQuantity(updatedQuantity);
            cartDetailRepository.save(cartDetail);

        }
        else{ // b3 tao cartdetail
            CartDetail cartDetailToSave = CartDetail.builder()
                    .quantity(rq.getQuantity())
                    .cart(cart)
                    .productDetail(productDetail)
                    .build();
            cartDetailRepository.save(cartDetailToSave);}

        return null;
    }
    @Transactional
    @Override
    public void updateQuantity(AddToCartRequest rq) {
        // lay accountId nguoi dung hien tai
        Long accountId = SecurityUtils.getCurrentAccountId()
                .orElseThrow(() -> new ResponseException(UnauthorizedError.UNAUTHORIZED));
        // tim gio hang nguoi dung
        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new ResponseException(InvalidInputError.CART_NOT_EXIST));
        // kiem tra san pham ton tai khong
        ProductDetail productDetail = productDetailRepository.findById(rq.getProductDetailId())
                .orElseThrow(() -> new ResponseException(InvalidInputError.PRODUCT_DETAIL_NOT_EXIST));
        // kiem tra san pham co trong gio hang chua
        CartDetail cartDetail = cartDetailRepository
                .findByCartIdAndProductDetail_Id(cart.getId(), productDetail.getId())
                .orElseThrow(() -> new ResponseException(InvalidInputError.CART_DETAIL_NOT_FOUND));

        // Nếu số lượng mới là 0 thì xoá luôn
        if (rq.getQuantity() == 0) {
            cartDetailRepository.delete(cartDetail);
        } else {
            if (rq.getQuantity() > productDetail.getQuantity()) {
                throw new ResponseException(InvalidInputError.QUANTITY_IS_NOT_ENOUGH, productDetail.getQuantity());
            }
            cartDetail.setQuantity(rq.getQuantity());
            cartDetailRepository.save(cartDetail);
        }
    }
    @Transactional
    @Override
    public void deleteFromCart(Long productDetailId) {
        Long accountId = SecurityUtils.getCurrentAccountId()
                .orElseThrow(() -> new ResponseException(UnauthorizedError.UNAUTHORIZED));

        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new ResponseException(InvalidInputError.CART_NOT_EXIST));

        CartDetail cartDetail = cartDetailRepository
                .findByCartIdAndProductDetail_Id(cart.getId(), productDetailId)
                .orElseThrow(() -> new ResponseException(InvalidInputError.CART_DETAIL_NOT_FOUND));

        cartDetailRepository.delete(cartDetail);
    }
    @Override
    public List<CartParam> getCartItems() {
        Long accountId = SecurityUtils.getCurrentAccountId()
                .orElseThrow(() -> new ResponseException(UnauthorizedError.UNAUTHORIZED));

        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new ResponseException(InvalidInputError.CART_NOT_EXIST));

        List<CartDetail> cartDetails = cartDetailRepository.findByCartId(cart.getId());

        return cartDetails.stream()
                .map(cd -> {
                    var pd = cd.getProductDetail();
                    return CartParam.builder()
                            .productDetailId(pd.getId())
                            .name(pd.getProduct().getName())
                            .color(pd.getColor().getName())
                            .size(pd.getSize().getName())
                            .quantity(cd.getQuantity())
                            .price(pd.getPrice())
                            .build();
                }).toList();
    }




}
