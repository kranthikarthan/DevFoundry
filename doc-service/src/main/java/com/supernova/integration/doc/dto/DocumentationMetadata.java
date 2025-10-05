package com.supernova.integration.doc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentationMetadata {
    private String difficulty; // BEGINNER, INTERMEDIATE, ADVANCED
    private Integer estimatedReadTime; // in minutes
    private String[] prerequisites;
    private String[] relatedTopics;
    private String sourceUrl;
    private Boolean isOfficial;
    private String license;
    private String lastReviewed;
    private String nextReviewDate;
}