package com.supernova.integration.doc.dto;

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
public class Documentation {
    private String id;
    private String title;
    private String description;
    private String integrationType;
    private String category;
    private String version;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> tags;
    private String content;
    private String contentType; // MARKDOWN, HTML, PLAIN_TEXT
    private List<DocumentationSection> sections;
    private DocumentationMetadata metadata;
}