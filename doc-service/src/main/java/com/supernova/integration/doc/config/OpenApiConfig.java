package com.supernova.integration.doc.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Documentation Service API",
                version = "1.0.0",
                description = "API for retrieving comprehensive documentation, guides, and tutorials for various integrations",
                contact = @Contact(
                        name = "Supernova Documentation Team",
                        email = "docs@supernova.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Development server",
                        url = "http://localhost:8083"
                ),
                @Server(
                        description = "Production server",
                        url = "https://api.supernova.com"
                )
        }
)
public class OpenApiConfig {
}