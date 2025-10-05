package com.supernova.integration.integration.controller;

import com.supernova.integration.integration.dto.IntegrationTemplate;
import com.supernova.integration.integration.dto.IntegrationType;
import com.supernova.integration.integration.service.IntegrationTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/integrations")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Integration Templates", description = "API for managing integration templates")
public class IntegrationTemplateController {

    private final IntegrationTemplateService integrationTemplateService;

    @GetMapping
    @Operation(summary = "Get all integration templates", description = "Retrieve a list of all available integration templates")
    public ResponseEntity<List<IntegrationTemplate>> getAllTemplates() {
        log.info("Fetching all integration templates");
        List<IntegrationTemplate> templates = integrationTemplateService.getAllTemplates();
        return ResponseEntity.ok(templates);
    }

    @GetMapping("/{type}")
    @Operation(summary = "Get integration templates by type", description = "Retrieve integration templates filtered by integration type")
    public ResponseEntity<List<IntegrationTemplate>> getTemplatesByType(
            @Parameter(description = "Integration type", example = "REST")
            @PathVariable IntegrationType type) {
        log.info("Fetching integration templates for type: {}", type);
        List<IntegrationTemplate> templates = integrationTemplateService.getTemplatesByType(type);
        return ResponseEntity.ok(templates);
    }

    @GetMapping("/{type}/{id}")
    @Operation(summary = "Get integration template by type and ID", description = "Retrieve a specific integration template by its type and ID")
    public ResponseEntity<IntegrationTemplate> getTemplateByTypeAndId(
            @Parameter(description = "Integration type", example = "REST")
            @PathVariable IntegrationType type,
            @Parameter(description = "Template ID", example = "rest-spring-boot")
            @PathVariable String id) {
        log.info("Fetching integration template for type: {} and id: {}", type, id);

        return integrationTemplateService.getTemplateByTypeAndId(type, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/types")
    @Operation(summary = "Get all integration types", description = "Retrieve all available integration types")
    public ResponseEntity<IntegrationType[]> getIntegrationTypes() {
        log.info("Fetching all integration types");
        return ResponseEntity.ok(IntegrationType.values());
    }
}