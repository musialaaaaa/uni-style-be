package org.example.uni_style_be.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.uni_style_be.entities.*;
import org.example.uni_style_be.enums.*;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.mapper.OrderMapper;
import org.example.uni_style_be.model.filter.OrderParam;
import org.example.uni_style_be.model.request.CreateOderRequest;
import org.example.uni_style_be.model.response.CreateOrderResponse;
import org.example.uni_style_be.model.response.OrderFilterResponse;
import org.example.uni_style_be.model.response.OrderResponse;
import org.example.uni_style_be.model.response.PageResponse;
import org.example.uni_style_be.repositories.*;
import org.example.uni_style_be.repositories.specification.OrderSpecification;
import org.example.uni_style_be.service.OrderService;
import org.example.uni_style_be.utils.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
    OrderDetailRepository orderDetailRepository;
    CartRepository cartRepository;
    CartDetailRepository cartDetailRepository;
    ProductDetailRepository productDetailRepository;
    CouponRepository couponRepository;
    OrderRepository orderRepository;
    PayOS payOS;
    PaymentRepository paymentRepository;
    OrderMapper orderMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public CreateOrderResponse createOrder(CreateOderRequest request) {

        // Lấy account đang đăng nhập
        Account currentAccount = SecurityUtils.getCurrentAccount()
                .orElseThrow(() -> new ResponseException(UnauthorizedError.UNAUTHORIZED));
        Long accountId = currentAccount.getId();

        // Lấy giỏ hàng của account đang đăng nhập
        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new ResponseException(NotFoundError.CART_NOT_FOUND));
        Long cartId = cart.getId();
        List<CartDetail> cartDetails = cartDetailRepository.findByCartId(cartId);

        if (CollectionUtils.isEmpty(cartDetails)) {
            throw new ResponseException(InvalidInputError.CART_DETAIL_NOT_FOUND);
        }

        // Lấy mã giảm giá
        Coupon coupon = null;
        if (Objects.nonNull(request.getCouponId())) {
            coupon = couponRepository.findById(request.getCouponId())
                    .orElseThrow(() -> new ResponseException(InvalidInputError.COUPON_NOT_FOUND));

            if (coupon.getExpirationDate().isBefore(LocalDate.now()) || coupon.getUsed() >= coupon.getUsageLimit()) {
                throw new ResponseException(InvalidInputError.COUPON_EXPIRED);
            }
            coupon.setUsed(coupon.getUsed() + 1);
        }

        // Khởi tạo đơn hàng
        Long orderCode = generateOrderCode();
        Order order = Order.builder()
                .code(orderCode)
                .totalAmount(BigDecimal.ZERO)
                .status(OrderStatus.PENDING)
                .fullName(request.getFullName())
                .shippingAddress(request.getShippingAddress())
                .phoneNumber(request.getPhoneNumber())
                .account(currentAccount)
                .coupon(coupon)
                .build();
        order = orderRepository.save(order);

        // Tính tiền
        int totalAmount = 0;
        List<ItemData> items = new ArrayList<>();
        List<OrderDetail> orderDetails = new ArrayList<>();
        List<ProductDetail> productDetails = new ArrayList<>();

        for (CartDetail cartDetail : cartDetails) {
            ProductDetail productDetail = cartDetail.getProductDetail();
            if (Objects.isNull(productDetail)) continue;
            String name = productDetail.getProduct().getName() + " - " + productDetail.getName();

            // Kiểm tra còn hàng không
            if (productDetail.getQuantity() < cartDetail.getQuantity()) {
                throw new ResponseException(InvalidInputError.PRODUCT_QUANTITY_IS_NOT_ENOUGH, name, productDetail.getQuantity());
            }
            Integer price = productDetail.getPrice().intValue();

            OrderDetail orderDetail = OrderDetail.builder()
                    .quantity(cartDetail.getQuantity())
                    .priceAtTime(productDetail.getPrice())
                    .productDetail(productDetail)
                    .order(order)
                    .build();

            ItemData item = ItemData.builder()
                    .name(name)
                    .quantity(cartDetail.getQuantity())
                    .price(price)
                    .build();

            // Trừ hàng còn lại trong kho
            productDetail.setQuantity(productDetail.getQuantity() - cartDetail.getQuantity());

            totalAmount += (price * cartDetail.getQuantity());
            orderDetails.add(orderDetail);
            items.add(item);
            productDetails.add(productDetail);
        }

        if (CollectionUtils.isEmpty(orderDetails)) {
            throw new ResponseException(InvalidInputError.CART_DETAIL_NOT_FOUND);
        }

        // Tính giảm giá
        if (Objects.nonNull(coupon)) {
            switch (coupon.getDiscountType()) {
                case VALUE -> totalAmount -= coupon.getValue().intValue();
                case PERCENT -> {
                    double discountPercentage = coupon.getValue().doubleValue();
                    double discountAmount = Math.round(totalAmount * discountPercentage / 100);
                    totalAmount -= (int) discountAmount;
                }
            }
        }

        totalAmount = Math.max(totalAmount, 0);
        order.setTotalAmount(BigDecimal.valueOf(totalAmount));

        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(request.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .paymentAmount(BigDecimal.valueOf(totalAmount))
                .build();

        if (Objects.requireNonNull(request.getPaymentMethod()) == PaymentMethod.BANK_TRANSFER) {
            long currentTimestamp = Instant.now().getEpochSecond();
            long timestampPlus10Min = (currentTimestamp + 600); // Đơn hàng hết hạn sau 10 phút
            PaymentData paymentData = PaymentData.builder()
                    .orderCode(orderCode)
                    .amount(totalAmount)
                    .description("Thanh toan " + orderCode)
                    .items(items)
                    .cancelUrl(request.getCancelUrl())
                    .returnUrl(request.getReturnUrl())
                    .expiredAt(timestampPlus10Min)
                    .build();

            try {
                CheckoutResponseData checkoutResponseData = payOS.createPaymentLink(paymentData);
                payment.setCheckoutUrl(checkoutResponseData.getCheckoutUrl());
                payment.setQrCode(checkoutResponseData.getQrCode());
                payment.setPartnerStatus(checkoutResponseData.getStatus());
                payment.setPaymentLinkId(checkoutResponseData.getPaymentLinkId());
                order.setExpiredAt(LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(timestampPlus10Min),
                        ZoneId.systemDefault()
                ));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new ResponseException(InternalServerError.INTERNAL_SERVER_ERROR);
            }
        } else if (Objects.equals(request.getPaymentMethod(), PaymentMethod.CASH)) {
            payment.setStatus(PaymentStatus.CONFIRMED);
            payment.setPaymentTime(LocalDateTime.now());
        }

        // Update database
        if (Objects.nonNull(coupon)) {
            couponRepository.save(coupon);
        }
        cartDetailRepository.deleteAll(cartDetails);
        productDetailRepository.saveAll(productDetails);
        payment = paymentRepository.save(payment);
        order = orderRepository.save(order);
        orderDetailRepository.saveAll(orderDetails);

        return CreateOrderResponse.builder()
                .id(order.getId())
                .code(order.getCode())
                .totalAmount(order.getTotalAmount())
                .checkoutUrl(payment.getCheckoutUrl())
                .qrCode(payment.getQrCode())
                .build();
    }

    @Transactional
    @Override
    public Void cancelOrder(Long orderCode) {
        // Lấy đơn hàng
        Order order = orderRepository.findByCode(orderCode)
                .orElseThrow(() -> new ResponseException(NotFoundError.ORDER_NOT_FOUND));
        if (!OrderStatus.PENDING.equals(order.getStatus())) {
            throw new ResponseException(InvalidInputError.ORDER_STATUS_INVALID);
        }
        order.setStatus(OrderStatus.CANCELLED);

        // Hoàn trả
        List<OrderDetail> orderDetails = order.getOrderDetails();
        List<ProductDetail> productDetails = orderDetails.stream().map(od -> {
            ProductDetail productDetail = od.getProductDetail();
            productDetail.setQuantity(productDetail.getQuantity() + od.getQuantity());
            return productDetail;
        }).toList();

        List<Payment> payments = order.getPayments();
        for (Payment payment : payments) {
            payment.setStatus(PaymentStatus.CANCELLED);
        }

        Coupon coupon = order.getCoupon();

        // Update database
        if (Objects.nonNull(coupon)) {
            coupon.setUsed(coupon.getUsed() - 1);
            couponRepository.save(coupon);
        }
        productDetailRepository.saveAll(productDetails);
        paymentRepository.saveAll(payments);
        orderRepository.save(order);

        return null;
    }

    @Override
    public OrderResponse detail(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseException(NotFoundError.ORDER_NOT_FOUND));
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public PageResponse<OrderFilterResponse> filter(OrderParam param, Pageable pageable) {
        Specification<Order> orderSpec = OrderSpecification.filterSpec(param);
        Page<Order> page = orderRepository.findAll(orderSpec, pageable);
        List<Order> rows = page.getContent();
        return new PageResponse<>(page.getTotalElements(), orderMapper.toOrderFilterResponse(rows));
    }

    @Override
    public PageResponse<OrderFilterResponse> filterMyOrder(OrderParam param, Pageable pageable) {
       String currentUsername = SecurityUtils.getCurrentUsername()
               .orElseThrow(() -> new ResponseException(UnauthorizedError.UNAUTHORIZED));
        Specification<Order> orderSpec = OrderSpecification.filterSpecMyOrder(param, currentUsername);
        Page<Order> page = orderRepository.findAll(orderSpec, pageable);
        List<Order> rows = page.getContent();
        return new PageResponse<>(page.getTotalElements(), orderMapper.toOrderFilterResponse(rows));
    }

    @Scheduled(fixedRate = 60000)
    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelOrderJob() {
        log.info("Start cancel order job");
        List<Order> expiredOrder
                = orderRepository.findByStatusAndExpiredAtBefore(OrderStatus.PENDING, LocalDateTime.now());
        if (expiredOrder.isEmpty()) {
            log.info("End cancel order job, {} orders cancelled", 0);
            return;
        }
        for (Order order : expiredOrder) {
            this.cancelOrder(order.getCode());
        }
        log.info("End cancel order job, {} orders cancelled", expiredOrder.size());
    }

    private long generateOrderCode() {
        String currentTimeString = String.valueOf(new Date().getTime());
        return Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));
    }

}
