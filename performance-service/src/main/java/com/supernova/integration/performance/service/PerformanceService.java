package com.supernova.integration.performance.service;

import com.supernova.integration.performance.dto.PerformanceReport;

import java.util.List;
import java.util.Optional;

public interface PerformanceService {
    List<PerformanceReport> getAllPerformanceReports();
    Optional<PerformanceReport> getPerformanceReportByType(String type);
    List<String> getAvailableIntegrationTypes();
}