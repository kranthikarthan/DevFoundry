package com.supernova.integration.integration.dto;

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
public class IntegrationTemplate {
    private String id;
    private String name;
    private String description;
    private IntegrationType type;
    private String category;
    private String language;
    private String framework;
    private String version;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> tags;
    private TemplateMetadata metadata;
    private String codeTemplate;
    private String configurationExample;
    private String usageExample;
    private PerformanceMetrics performanceMetrics;
    private ReliabilityMetrics reliabilityMetrics;
}