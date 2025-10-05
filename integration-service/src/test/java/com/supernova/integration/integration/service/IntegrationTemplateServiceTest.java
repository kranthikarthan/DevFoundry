package com.supernova.integration.integration.service;

import com.supernova.integration.integration.dto.IntegrationTemplate;
import com.supernova.integration.integration.dto.IntegrationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class IntegrationTemplateServiceTest {

    @Autowired
    private IntegrationTemplateService integrationTemplateService;

    @Test
    @DisplayName("Should return all integration templates")
    void shouldReturnAllIntegrationTemplates() {
        // When
        List<IntegrationTemplate> templates = integrationTemplateService.getAllTemplates();

        // Then
        assertThat(templates).isNotNull();
        assertThat(templates).hasSize(4); // REST, Kafka, SOAP, Database

        // Verify each template has required fields
        templates.forEach(template -> {
            assertThat(template.getId()).isNotBlank();
            assertThat(template.getName()).isNotBlank();
            assertThat(template.getType()).isNotNull();
            assertThat(template.getMetadata()).isNotNull();
            assertThat(template.getPerformanceMetrics()).isNotNull();
            assertThat(template.getReliabilityMetrics()).isNotNull();
        });
    }

    @Test
    @DisplayName("Should return templates filtered by type")
    void shouldReturnTemplatesFilteredByType() {
        // When
        List<IntegrationTemplate> restTemplates = integrationTemplateService.getTemplatesByType(IntegrationType.REST);
        List<IntegrationTemplate> kafkaTemplates = integrationTemplateService.getTemplatesByType(IntegrationType.KAFKA);

        // Then
        assertThat(restTemplates).hasSize(1);
        assertThat(kafkaTemplates).hasSize(1);

        assertThat(restTemplates.get(0).getType()).isEqualTo(IntegrationType.REST);
        assertThat(kafkaTemplates.get(0).getType()).isEqualTo(IntegrationType.KAFKA);
    }

    @Test
    @DisplayName("Should return template by ID")
    void shouldReturnTemplateById() {
        // When
        Optional<IntegrationTemplate> template = integrationTemplateService.getTemplateById("rest-spring-boot");

        // Then
        assertThat(template).isPresent();
        assertThat(template.get().getId()).isEqualTo("rest-spring-boot");
        assertThat(template.get().getType()).isEqualTo(IntegrationType.REST);
    }

    @Test
    @DisplayName("Should return empty when template not found")
    void shouldReturnEmptyWhenTemplateNotFound() {
        // When
        Optional<IntegrationTemplate> template = integrationTemplateService.getTemplateById("non-existent-id");

        // Then
        assertThat(template).isEmpty();
    }

    @Test
    @DisplayName("Should return template by type and ID")
    void shouldReturnTemplateByTypeAndId() {
        // When
        Optional<IntegrationTemplate> template = integrationTemplateService.getTemplateByTypeAndId(
                IntegrationType.REST, "rest-spring-boot");

        // Then
        assertThat(template).isPresent();
        assertThat(template.get().getId()).isEqualTo("rest-spring-boot");
        assertThat(template.get().getType()).isEqualTo(IntegrationType.REST);
    }

    @Test
    @DisplayName("Should return empty when template by type and ID not found")
    void shouldReturnEmptyWhenTemplateByTypeAndIdNotFound() {
        // When
        Optional<IntegrationTemplate> template = integrationTemplateService.getTemplateByTypeAndId(
                IntegrationType.REST, "non-existent-id");

        // Then
        assertThat(template).isEmpty();
    }
}