package com.supernova.integration.performance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceMetrics {
    private Double responseTime; // milliseconds
    private Double throughput; // requests per second
    private Double memoryUsage; // MB
    private Double cpuUsage; // percentage
    private Integer concurrentUsers;
    private Double errorRate; // percentage
    private String grade; // A, B, C, D, F
    private LocalDateTime timestamp;
    private String testScenario;
}