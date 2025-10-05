package com.supernova.integration.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class IntegrationServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    static GenericContainer<?> postgres = new GenericContainer<>("postgres:15-alpine")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_DB", "testdb")
            .withEnv("POSTGRES_USER", "test")
            .withEnv("POSTGRES_PASSWORD", "test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> String.format("jdbc:postgresql://localhost:%d/testdb", postgres.getFirstMappedPort()));
        registry.add("spring.datasource.username", () -> "test");
        registry.add("spring.datasource.password", () -> "test");
    }

    @Test
    @DisplayName("Should return all integration templates")
    void shouldReturnAllIntegrationTemplates() {
        // When
        ResponseEntity<String> response = restTemplate.getForEntity("/api/integrations", String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("integrationTemplate");
    }

    @Test
    @DisplayName("Should return integration types")
    void shouldReturnIntegrationTypes() {
        // When
        ResponseEntity<String> response = restTemplate.getForEntity("/api/integrations/types", String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("REST");
        assertThat(response.getBody()).contains("KAFKA");
        assertThat(response.getBody()).contains("SOAP");
        assertThat(response.getBody()).contains("DATABASE");
    }

    @Test
    @DisplayName("Should return REST templates")
    void shouldReturnRestTemplates() {
        // When
        ResponseEntity<String> response = restTemplate.getForEntity("/api/integrations/REST", String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("Spring Boot REST API");
    }

    @Test
    @DisplayName("Should return health status")
    void shouldReturnHealthStatus() {
        // When
        ResponseEntity<String> response = restTemplate.getForEntity("/actuator/health", String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("UP");
    }

    @Test
    @DisplayName("Should return metrics in Prometheus format")
    void shouldReturnMetricsInPrometheusFormat() {
        // When
        ResponseEntity<String> response = restTemplate.getForEntity("/actuator/prometheus", String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("# HELP");
        assertThat(response.getBody()).contains("# TYPE");
    }
}