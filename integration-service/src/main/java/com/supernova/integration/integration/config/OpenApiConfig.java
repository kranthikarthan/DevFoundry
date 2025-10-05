package com.supernova.integration.integration.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Integration Service API",
                version = "1.0.0",
                description = "API for managing integration templates with code examples, performance metrics, and reliability data",
                contact = @Contact(
                        name = "Supernova Integration Team",
                        email = "integration@supernova.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Development server",
                        url = "http://localhost:8081"
                ),
                @Server(
                        description = "Production server",
                        url = "https://api.supernova.com"
                )
        }
)
public class OpenApiConfig {
}