package org.example.uni_style_be.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.entities.Payment;
import org.example.uni_style_be.enums.AccountType;
import org.example.uni_style_be.enums.InvalidInputError;
import org.example.uni_style_be.enums.OrderStatus;
import org.example.uni_style_be.enums.PaymentStatus;
import org.example.uni_style_be.exception.ResponseException;
import org.example.uni_style_be.model.response.DailyReportDto;
import org.example.uni_style_be.model.response.DashboardResponse;
import org.example.uni_style_be.repositories.AccountRepository;
import org.example.uni_style_be.repositories.OrderRepository;
import org.example.uni_style_be.repositories.PaymentRepository;
import org.example.uni_style_be.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticsServiceImpl implements StatisticsService {

    OrderRepository orderRepository;
    PaymentRepository paymentRepository;
    AccountRepository accountRepository;

    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    @Override
    public DashboardResponse buildDashboard(LocalDate startDate, LocalDate endDate) {
        DashboardResponse response = new DashboardResponse();

        try {
            LocalDateTime startLocalDateTime = startDate.atStartOfDay();
            LocalDateTime endLocalDateTime = endDate.atTime(23, 59, 59);

            if (startLocalDateTime.isAfter(endLocalDateTime)) {
                throw new ResponseException(InvalidInputError.START_TIME_GR_END_TIME);
            }

            Instant startTime = startLocalDateTime.atZone(DEFAULT_ZONE).toInstant();
            Instant endTime = endLocalDateTime.atZone(DEFAULT_ZONE).toInstant();

            long totalOrder = orderRepository.countByStatusAndCreatedAtBetween(
                    OrderStatus.CONFIRMED, startTime, endTime
            );

            List<Payment> payments = paymentRepository.findByStatusAndPaymentTimeBetweenOrderByPaymentTime(
                    PaymentStatus.CONFIRMED, startLocalDateTime, endLocalDateTime
            );

            List<DailyReportDto> dailyReports = createDailyReportsWithFullRange(
                    payments, startDate, endDate
            );

            BigDecimal totalRevenue = payments.stream()
                    .map(Payment::getPaymentAmount)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            long totalNewCustomer = accountRepository.countByTypeAndCreatedAtBetween(
                    AccountType.CUSTOMER, startTime, endTime
            );

            response.setTotalOrder(totalOrder);
            response.setTotalRevenue(totalRevenue);
            response.setTotalNewCustomer(totalNewCustomer);
            response.setDailyReports(dailyReports);
        } catch (DateTimeParseException e) {
            throw new ResponseException(InvalidInputError.INVALID_DATE);
        }

        return response;
    }

    private List<DailyReportDto> createDailyReportsWithFullRange(
            List<Payment> payments, LocalDate startDate, LocalDate endDate) {

        // 1. Filter và group payments theo ngày
        Map<LocalDate, List<Payment>> paymentsByDate = payments.stream()
                .filter(payment -> payment.getPaymentTime() != null) // Loại bỏ paymentTime null
                .filter(payment -> {
                    LocalDate paymentDate = payment.getPaymentTime().toLocalDate();
                    // Đảm bảo payment trong khoảng thời gian yêu cầu
                    return !paymentDate.isBefore(startDate) && !paymentDate.isAfter(endDate);
                })
                .collect(Collectors.groupingBy(payment ->
                        payment.getPaymentTime().toLocalDate()
                ));

        // 2. Tạo danh sách đầy đủ từ startDate đến endDate
        List<DailyReportDto> dailyReports = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            // Lấy payments của ngày hiện tại (empty list nếu không có)
            List<Payment> dailyPayments = paymentsByDate.getOrDefault(currentDate, Collections.emptyList());

            // Tính tổng revenue với null safety
            BigDecimal dailyRevenue = dailyPayments.stream()
                    .map(Payment::getPaymentAmount)
                    .filter(Objects::nonNull) // Loại bỏ paymentAmount null
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Tạo DailyReportDto cho ngày hiện tại
            DailyReportDto dailyReport = DailyReportDto.builder()
                    .date(currentDate)
                    .revenue(dailyRevenue) // Sẽ là 0 nếu không có payments
                    .build();

            dailyReports.add(dailyReport);
            currentDate = currentDate.plusDays(1);
        }

        return dailyReports;
    }
}
