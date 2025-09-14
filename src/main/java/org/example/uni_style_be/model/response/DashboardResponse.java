package org.example.uni_style_be.model.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardResponse {

    long totalOrder = 0L;

    BigDecimal totalRevenue = BigDecimal.ZERO;

    long totalNewCustomer = 0L;

    List<DailyReportDto> dailyReports = new ArrayList<>();
}
