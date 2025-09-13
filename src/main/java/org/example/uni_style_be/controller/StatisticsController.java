package org.example.uni_style_be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.uni_style_be.model.response.DashboardResponse;
import org.example.uni_style_be.model.response.ServiceResponse;
import org.example.uni_style_be.service.StatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@Tag(name = "Thống kê")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(summary = "Dashboard Admin")
    @GetMapping("/dashboard")
    public ServiceResponse<DashboardResponse> getDashboard(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate
            ) {
        return ServiceResponse.ok(statisticsService.buildDashboard(startDate, endDate));
    }

}
