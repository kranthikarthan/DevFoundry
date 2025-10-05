package com.supernova.integration.metrics.controller;

import com.supernova.integration.metrics.service.MetricsService;
import io.micrometer.core.instrument.Timer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Metrics", description = "API for custom metrics and monitoring")
public class MetricsController {

    private final MetricsService metricsService;

    @GetMapping("/simulate")
    @Operation(summary = "Simulate integration request", description = "Simulate an integration request for testing metrics")
    public ResponseEntity<Map<String, String>> simulateIntegrationRequest() {
        log.info("Simulating integration request");

        Timer.Sample sample = metricsService.startProcessingTimer();

        try {
            // Simulate some processing time
            Thread.sleep(100);

            metricsService.recordIntegrationRequest();

            Map<String, String> response = new HashMap<>();
            response.put("message", "Integration request simulated");
            response.put("status", "success");

            return ResponseEntity.ok(response);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.internalServerError().build();
        } finally {
            metricsService.stopProcessingTimer(sample);
        }
    }

    @GetMapping("/simulate-download")
    @Operation(summary = "Simulate template download", description = "Simulate a template download for testing metrics")
    public ResponseEntity<Map<String, String>> simulateTemplateDownload() {
        log.info("Simulating template download");

        metricsService.recordTemplateDownload();

        Map<String, String> response = new HashMap<>();
        response.put("message", "Template download simulated");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/simulate-docs")
    @Operation(summary = "Simulate documentation view", description = "Simulate a documentation view for testing metrics")
    public ResponseEntity<Map<String, String>> simulateDocumentationView() {
        log.info("Simulating documentation view");

        metricsService.recordDocumentationView();

        Map<String, String> response = new HashMap<>();
        response.put("message", "Documentation view simulated");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    @Operation(summary = "Metrics service health", description = "Check the health status of the metrics service")
    public ResponseEntity<Map<String, Object>> getHealth() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "metrics-service");
        health.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(health);
    }
}