package com.supernova.integration.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceMetrics {
    private Double averageResponseTime; // in milliseconds
    private Double throughput; // requests per second
    private Double memoryUsage; // in MB
    private Double cpuUsage; // percentage
    private Integer concurrentUsers; // max supported
    private String performanceGrade; // A, B, C, D, F
    private String lastBenchmarked;
}