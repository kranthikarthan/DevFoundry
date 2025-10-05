package com.supernova.integration.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateMetadata {
    private String difficulty; // BEGINNER, INTERMEDIATE, ADVANCED
    private Integer estimatedSetupTime; // in minutes
    private String prerequisites;
    private String[] requiredDependencies;
    private String[] optionalDependencies;
    private String documentationUrl;
    private String sourceUrl;
    private Boolean isProductionReady;
    private String license;
}