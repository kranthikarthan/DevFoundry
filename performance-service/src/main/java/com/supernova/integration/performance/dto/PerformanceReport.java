package com.supernova.integration.performance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReport {
    private String id;
    private String integrationType;
    private String integrationName;
    private LocalDateTime reportDate;
    private String environment;
    private PerformanceMetrics overall;
    private List<PerformanceMetrics> benchmarks;
    private List<PerformanceIssue> issues;
    private PerformanceRecommendations recommendations;
}