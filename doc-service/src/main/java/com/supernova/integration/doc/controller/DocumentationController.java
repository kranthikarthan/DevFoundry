package com.supernova.integration.doc.controller;

import com.supernova.integration.doc.dto.Documentation;
import com.supernova.integration.doc.service.DocumentationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docs")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Documentation", description = "API for retrieving documentation and guides")
public class DocumentationController {

    private final DocumentationService documentationService;

    @GetMapping
    @Operation(summary = "Get all documentation", description = "Retrieve all available documentation")
    public ResponseEntity<List<Documentation>> getAllDocumentation() {
        log.info("Fetching all documentation");
        List<Documentation> docs = documentationService.getAllDocumentation();
        return ResponseEntity.ok(docs);
    }

    @GetMapping("/{type}")
    @Operation(summary = "Get documentation by integration type", description = "Retrieve documentation for a specific integration type")
    public ResponseEntity<Documentation> getDocumentationByType(
            @Parameter(description = "Integration type", example = "REST")
            @PathVariable String type) {
        log.info("Fetching documentation for type: {}", type);

        return documentationService.getDocumentationByType(type)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/types")
    @Operation(summary = "Get available documentation types", description = "Retrieve list of all available documentation types")
    public ResponseEntity<List<String>> getAvailableDocumentationTypes() {
        log.info("Fetching available documentation types");
        List<String> types = documentationService.getAvailableDocumentationTypes();
        return ResponseEntity.ok(types);
    }
}