package com.supernova.integration.metrics.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Metrics Service API",
                version = "1.0.0",
                description = "API for custom metrics collection and monitoring with Prometheus integration",
                contact = @Contact(
                        name = "Supernova SRE Team",
                        email = "sre@supernova.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Development server",
                        url = "http://localhost:8084"
                ),
                @Server(
                        description = "Production server",
                        url = "https://api.supernova.com"
                )
        }
)
public class OpenApiConfig {
}