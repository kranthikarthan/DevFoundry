package com.supernova.integration.performance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceIssue {
    private String id;
    private String severity; // CRITICAL, HIGH, MEDIUM, LOW
    private String category; // MEMORY, CPU, NETWORK, DATABASE, CODE
    private String description;
    private String impact;
    private String solution;
    private Boolean isResolved;
    private LocalDateTime detectedAt;
    private LocalDateTime resolvedAt;
}