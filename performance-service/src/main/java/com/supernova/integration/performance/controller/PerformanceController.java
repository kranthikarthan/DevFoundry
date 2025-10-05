package com.supernova.integration.performance.controller;

import com.supernova.integration.performance.dto.PerformanceReport;
import com.supernova.integration.performance.service.PerformanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Performance Reports", description = "API for retrieving performance metrics and reports")
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping
    @Operation(summary = "Get all performance reports", description = "Retrieve all available performance reports")
    public ResponseEntity<List<PerformanceReport>> getAllPerformanceReports() {
        log.info("Fetching all performance reports");
        List<PerformanceReport> reports = performanceService.getAllPerformanceReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{type}")
    @Operation(summary = "Get performance report by integration type", description = "Retrieve performance report for a specific integration type")
    public ResponseEntity<PerformanceReport> getPerformanceReportByType(
            @Parameter(description = "Integration type", example = "REST")
            @PathVariable String type) {
        log.info("Fetching performance report for type: {}", type);

        return performanceService.getPerformanceReportByType(type)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/types")
    @Operation(summary = "Get available integration types", description = "Retrieve list of all available integration types")
    public ResponseEntity<List<String>> getAvailableIntegrationTypes() {
        log.info("Fetching available integration types");
        List<String> types = performanceService.getAvailableIntegrationTypes();
        return ResponseEntity.ok(types);
    }
}