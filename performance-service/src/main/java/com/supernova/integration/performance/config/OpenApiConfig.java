package com.supernova.integration.performance.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Performance Service API",
                version = "1.0.0",
                description = "API for retrieving performance metrics, benchmarks, and recommendations for various integrations",
                contact = @Contact(
                        name = "Supernova Performance Team",
                        email = "performance@supernova.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Development server",
                        url = "http://localhost:8082"
                ),
                @Server(
                        description = "Production server",
                        url = "https://api.supernova.com"
                )
        }
)
public class OpenApiConfig {
}