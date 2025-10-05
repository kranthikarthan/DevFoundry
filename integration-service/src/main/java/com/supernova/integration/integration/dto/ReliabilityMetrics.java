package com.supernova.integration.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReliabilityMetrics {
    private Double uptime; // percentage (99.9%)
    private Double availability; // percentage
    private Double errorRate; // percentage
    private Integer meanTimeBetweenFailures; // in hours
    private Integer meanTimeToRecovery; // in minutes
    private String reliabilityGrade; // A, B, C, D, F
    private String lastIncident;
    private String monitoringUrl;
}