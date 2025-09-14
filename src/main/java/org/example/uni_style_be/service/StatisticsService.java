package org.example.uni_style_be.service;

import org.example.uni_style_be.model.response.DashboardResponse;

import java.time.LocalDate;

public interface StatisticsService {
    DashboardResponse buildDashboard(LocalDate startDate, LocalDate endDate);
}
