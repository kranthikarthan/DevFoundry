package com.supernova.integration.metrics.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetricsService {

    private final MeterRegistry meterRegistry;

    // Counters for tracking various events
    private Counter integrationRequestsTotal;
    private Counter templateDownloadsTotal;
    private Counter documentationViewsTotal;

    // Gauges for current state
    private AtomicInteger activeUsers = new AtomicInteger(0);
    private AtomicInteger availableTemplates = new AtomicInteger(0);

    // Timers for measuring performance
    private Timer integrationProcessingTime;

    @PostConstruct
    public void init() {
        // Initialize counters
        integrationRequestsTotal = Counter.builder("integration_requests_total")
                .description("Total number of integration requests")
                .tags("service", "integration-portal")
                .register(meterRegistry);

        templateDownloadsTotal = Counter.builder("template_downloads_total")
                .description("Total number of template downloads")
                .tags("service", "integration-portal")
                .register(meterRegistry);

        documentationViewsTotal = Counter.builder("documentation_views_total")
                .description("Total number of documentation views")
                .tags("service", "integration-portal")
                .register(meterRegistry);

        // Initialize gauges
        Gauge.builder("active_users_current")
                .description("Current number of active users")
                .register(meterRegistry, activeUsers, AtomicInteger::doubleValue);

        Gauge.builder("available_templates")
                .description("Number of available integration templates")
                .register(meterRegistry, availableTemplates, AtomicInteger::doubleValue);

        // Initialize timer
        integrationProcessingTime = Timer.builder("integration_processing_seconds")
                .description("Time spent processing integration requests")
                .register(meterRegistry);

        // Set initial values
        availableTemplates.set(4); // REST, Kafka, SOAP, Database

        // Start background metrics simulation
        startMetricsSimulation();
    }

    public void recordIntegrationRequest() {
        integrationRequestsTotal.increment();
        simulateActiveUser();
    }

    public void recordTemplateDownload() {
        templateDownloadsTotal.increment();
    }

    public void recordDocumentationView() {
        documentationViewsTotal.increment();
        simulateActiveUser();
    }

    public Timer.Sample startProcessingTimer() {
        return Timer.start(meterRegistry);
    }

    public void stopProcessingTimer(Timer.Sample sample) {
        sample.stop(integrationProcessingTime);
    }

    private void simulateActiveUser() {
        // Simulate user activity - randomly increment/decrement active users
        int currentActive = activeUsers.get();
        int change = ThreadLocalRandom.current().nextInt(-1, 2); // -1, 0, or 1
        int newActive = Math.max(0, currentActive + change);
        activeUsers.set(newActive);
    }

    private void startMetricsSimulation() {
        // Simulate periodic metrics updates (in a real app, this would come from actual usage)
        Thread simulationThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(Duration.ofSeconds(30).toMillis());

                    // Simulate random events
                    if (ThreadLocalRandom.current().nextDouble() < 0.3) {
                        recordIntegrationRequest();
                    }
                    if (ThreadLocalRandom.current().nextDouble() < 0.2) {
                        recordTemplateDownload();
                    }
                    if (ThreadLocalRandom.current().nextDouble() < 0.4) {
                        recordDocumentationView();
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        simulationThread.setDaemon(true);
        simulationThread.setName("Metrics-Simulation");
        simulationThread.start();
    }
}