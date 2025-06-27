package org.example.uni_style_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.model.request.CreateOderRequest;
import org.example.uni_style_be.service.OrderService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    public void createOrder(CreateOderRequest request) {
        //B1: có được user_id

        //B2:  lấy giỏ hàng qua user_id

        //B3: lấy những sản pẩm trong giỏ hàng(product_detail_id) và số lượng

        //B4: lấy các product_detail qua danh sách product_detail_id

        //B5: lấy mã giảm giá

        //B6: tính tổng tien

        //B7: insert bảng order

        //B8: lặp product_detail mỗi lần lặp đều insert vào bảng orderDetail và ập nhật lại trong bảng productDetail dồng thời đặt phải trừ đi số lượng và validate số lượng

        //B9: payment insert bản ghi ở trạng thái pending vào bản payment trong trường hợp phương thức thanh toán chuyển khoản ngân hàng thì gọi sang paymantgetway để lay link thanh toán
    }
}
