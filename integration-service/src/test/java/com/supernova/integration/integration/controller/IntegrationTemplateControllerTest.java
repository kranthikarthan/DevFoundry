package com.supernova.integration.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supernova.integration.integration.dto.IntegrationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IntegrationTemplateController.class)
class IntegrationTemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private com.supernova.integration.integration.service.IntegrationTemplateService integrationTemplateService;

    @Test
    @DisplayName("Should return all templates")
    void shouldReturnAllTemplates() throws Exception {
        // Given
        List<com.supernova.integration.integration.dto.IntegrationTemplate> templates = Arrays.asList(
                com.supernova.integration.integration.dto.IntegrationTemplate.builder()
                        .id("test-1")
                        .name("Test Template 1")
                        .type(IntegrationType.REST)
                        .build(),
                com.supernova.integration.integration.dto.IntegrationTemplate.builder()
                        .id("test-2")
                        .name("Test Template 2")
                        .type(IntegrationType.KAFKA)
                        .build()
        );

        when(integrationTemplateService.getAllTemplates()).thenReturn(templates);

        // When & Then
        mockMvc.perform(get("/api/integrations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("test-1"))
                .andExpect(jsonPath("$[1].id").value("test-2"));
    }

    @Test
    @DisplayName("Should return templates by type")
    void shouldReturnTemplatesByType() throws Exception {
        // Given
        List<com.supernova.integration.integration.dto.IntegrationTemplate> templates = Arrays.asList(
                com.supernova.integration.integration.dto.IntegrationTemplate.builder()
                        .id("rest-1")
                        .name("REST Template")
                        .type(IntegrationType.REST)
                        .build()
        );

        when(integrationTemplateService.getTemplatesByType(IntegrationType.REST)).thenReturn(templates);

        // When & Then
        mockMvc.perform(get("/api/integrations/REST"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].type").value("REST"));
    }

    @Test
    @DisplayName("Should return template by type and ID")
    void shouldReturnTemplateByTypeAndId() throws Exception {
        // Given
        com.supernova.integration.integration.dto.IntegrationTemplate template =
                com.supernova.integration.integration.dto.IntegrationTemplate.builder()
                        .id("rest-spring-boot")
                        .name("Spring Boot REST API")
                        .type(IntegrationType.REST)
                        .build();

        when(integrationTemplateService.getTemplateByTypeAndId(eq(IntegrationType.REST), any(String)))
                .thenReturn(Optional.of(template));

        // When & Then
        mockMvc.perform(get("/api/integrations/REST/rest-spring-boot"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("rest-spring-boot"))
                .andExpect(jsonPath("$.type").value("REST"));
    }

    @Test
    @DisplayName("Should return 404 when template not found")
    void shouldReturn404WhenTemplateNotFound() throws Exception {
        // Given
        when(integrationTemplateService.getTemplateByTypeAndId(eq(IntegrationType.REST), any(String)))
                .thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/integrations/REST/non-existent"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return integration types")
    void shouldReturnIntegrationTypes() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/integrations/types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(IntegrationType.values().length));
    }
}