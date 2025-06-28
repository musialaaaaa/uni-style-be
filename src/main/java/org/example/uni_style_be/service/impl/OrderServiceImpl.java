package org.example.uni_style_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.entities.*;
import org.example.uni_style_be.enums.NotFoundError;
import org.example.uni_style_be.enums.OrderStatus;
import org.example.uni_style_be.enums.UnauthorizedError;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.model.request.CreateOderRequest;
import org.example.uni_style_be.repositories.*;
import org.example.uni_style_be.service.OrderService;
import org.example.uni_style_be.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDetailRepository orderDetailRepository;

    private final ShoppingCartReposotory shoppingCartReposotory;

    private final CartDetailRepository cartDetailRepository;

    private final ProductDetailRepository productDetailRepository;

    private final CouponRepository couponRepository; // Assuming you have a CouponRepository to fetch coupons

    private final AccountRepository accountRepository; // Assuming you have an AccountRepository to fetch account details

    private final OrderRepository orderRepository;

    public void createOrder(CreateOderRequest request) {
        //B1: có được user_id
        Optional<Long> accountIdOptional = SecurityUtils.getCurrentAccountId();
        if (accountIdOptional.isEmpty()) {
            throw new ResponseException(UnauthorizedError.UNAUTHORIZED);
        }
        Long accountId = accountIdOptional.get();

        //B2:  lấy giỏ hàng qua user_id
        Cart cart = shoppingCartReposotory.findByAccountId(accountId);
        if (cart == null) {
            throw new ResponseException(NotFoundError.CART_NOT_FOUND);
        }
        Long cartId = cart.getId();
        //B3: lấy những sản pẩm trong giỏ hàng(product_detail_id) và số lượng
        List<CartDetail> cartDetails = cartDetailRepository.findByCartId(cartId);
        //B4: lấy các product_detail qua danh sách product_detail_id
        List<Long> productDetailIds = cartDetails.stream()
                .flatMap(cartDetail -> cartDetail.getProductDetails().stream())
                .map(ProductDetail::getId)
                .collect(Collectors.toList());
        List<ProductDetail> productDetails = productDetailRepository.findAllById(productDetailIds);
        //B5: lấy mã giảm giá
        Optional<Coupon> coupon = Optional.empty();
        if (request.getVoucherId() != null) {
            coupon = couponRepository.findById(request.getVoucherId());
            if (coupon.isEmpty()) {
                throw new ResponseException(NotFoundError.COUPON_NOT_FOUND);
            }
        }
        //B6: tính tổng tien
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartDetail cartDetail : cartDetails) {
            ProductDetail productDetail = productDetails.stream()
                    .filter(pd -> pd.getId().equals(cartDetail.getProductDetails().get(0).getId()))
                    .findFirst()
                    .orElseThrow(() -> new ResponseException(NotFoundError.PRODUCT_DETAIL_NOT_FOUND));
            totalAmount = totalAmount.add(productDetail.getPrice().multiply(BigDecimal.valueOf(cartDetail.getQuantity())));
        }
        if (coupon.isPresent()) {
            // Giả sử coupon có một phương thức để tính toán giảm giá
            totalAmount = totalAmount.subtract(coupon.get().getValue());
        }
        //B7: insert bảng order
        Order order = new Order();
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING); // Trạng thái ban đầu là PENDING
        order.setShippingAddress(request.getShippingAddress());
        order.setPhoneNumber(request.getPhoneNumber());
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseException(NotFoundError.ACCOUNT_NOT_FOUND));
        order.setAccount(account);
        order.setVoucher(coupon.orElse(null)); // Nếu có mã giảm giá thì set vào, nếu không thì để null
        orderRepository.save(order);
        //B8: lặp product_detail mỗi lần lặp đều insert vào bảng orderDetail và ập nhật lại trong bảng productDetail dồng thời đặt phải trừ đi số lượng và validate số lượng
        for (CartDetail cartDetail : cartDetails) {
            if (cartDetail.getProductDetails().isEmpty()) {
                throw new ResponseException(NotFoundError.PRODUCT_DETAIL_NOT_FOUND);
            }
            ProductDetail productDetail = cartDetail.getProductDetails().get(0);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProductDetailId(productDetail);
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setPriceAtTime(productDetail.getPrice());

            int newQuantity = productDetail.getQuantity() - cartDetail.getQuantity();
            if (newQuantity < 0) {
                throw new ResponseException(NotFoundError.QUANTITY_NOT_FOUND);
            }
            productDetail.setQuantity(newQuantity);
            orderDetailRepository.save(orderDetail); // Lưu OrderDetail trong vòng lặp
        }
        productDetailRepository.saveAll(productDetails); // Lưu tất cả ProductDetail một lần
        //B9: payment insert bản ghi ở trạng thái pending vào bản payment trong trường hợp phương thức thanh toán chuyển khoản ngân hàng thì gọi sang paymantgetway để lay link thanh toán
    }
}
