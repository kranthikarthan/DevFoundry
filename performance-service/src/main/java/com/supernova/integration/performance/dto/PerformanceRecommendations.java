package com.supernova.integration.performance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceRecommendations {
    private String overallGrade; // A, B, C, D, F
    private List<String> immediateActions;
    private List<String> shortTermImprovements;
    private List<String> longTermOptimizations;
    private String nextReviewDate;
    private String monitoringStrategy;
}