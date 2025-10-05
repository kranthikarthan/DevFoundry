package com.supernova.integration.doc.service;

import com.supernova.integration.doc.dto.Documentation;

import java.util.List;
import java.util.Optional;

public interface DocumentationService {
    List<Documentation> getAllDocumentation();
    Optional<Documentation> getDocumentationByType(String type);
    List<String> getAvailableDocumentationTypes();
}