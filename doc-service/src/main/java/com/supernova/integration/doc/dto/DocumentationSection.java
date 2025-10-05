package com.supernova.integration.doc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentationSection {
    private String id;
    private String title;
    private String content;
    private Integer order;
    private String level; // H1, H2, H3, etc.
    private List<String> codeExamples;
    private List<String> relatedLinks;
}