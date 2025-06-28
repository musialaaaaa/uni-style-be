package org.example.uni_style_be.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    @NotBlank(message = "Đơn hàng không được để trống")
    private LocalDateTime orderDate;

    private BigDecimal totalAmount;

    private String status;

    private String shippingAddress;

    private Long userId; // Id người đặt hàng

    private Long voucherId; // Nếu có mã giảm giá

    private List<OrderDetailRequest> orderDetails; // Danh sách sản phẩm đặt mua

//    private PaymentRequest payment; // Thông tin thanh toán

    // Tạo thêm các class con: OrderDetailRequest, PaymentRequest cho phù hợp
}