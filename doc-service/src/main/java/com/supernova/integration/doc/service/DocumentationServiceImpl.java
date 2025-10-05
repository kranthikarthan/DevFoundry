package com.supernova.integration.doc.service;

import com.supernova.integration.doc.dto.Documentation;
import com.supernova.integration.doc.dto.DocumentationMetadata;
import com.supernova.integration.doc.dto.DocumentationSection;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentationServiceImpl implements DocumentationService {

    @Override
    public List<Documentation> getAllDocumentation() {
        return Arrays.asList(
                createRestDocumentation(),
                createKafkaDocumentation(),
                createSoapDocumentation(),
                createDatabaseDocumentation()
        );
    }

    @Override
    public Optional<Documentation> getDocumentationByType(String type) {
        return getAllDocumentation().stream()
                .filter(doc -> doc.getIntegrationType().equalsIgnoreCase(type))
                .findFirst();
    }

    @Override
    public List<String> getAvailableDocumentationTypes() {
        return Arrays.asList("REST", "KAFKA", "SOAP", "DATABASE", "FILE", "CLOUD");
    }

    private Documentation createRestDocumentation() {
        return Documentation.builder()
                .id("doc-rest-001")
                .title("REST API Integration Guide")
                .description("Complete guide to building REST APIs with Spring Boot")
                .integrationType("REST")
                .category("Web Services")
                .version("1.0.0")
                .author("Supernova Documentation Team")
                .createdAt(LocalDateTime.now().minusDays(30))
                .updatedAt(LocalDateTime.now().minusDays(5))
                .tags(Arrays.asList("rest", "api", "spring-boot", "http"))
                .contentType("MARKDOWN")
                .sections(Arrays.asList(
                        DocumentationSection.builder()
                                .id("rest-intro")
                                .title("Introduction")
                                .content("""
                                        # REST API Integration Guide

                                        This comprehensive guide will walk you through creating production-ready REST APIs using Spring Boot.

                                        ## What You'll Learn

                                        - Setting up a Spring Boot REST project
                                        - Creating REST controllers and endpoints
                                        - Handling HTTP methods (GET, POST, PUT, DELETE)
                                        - Request/Response handling with DTOs
                                        - Error handling and validation
                                        - Testing REST APIs
                                        """)
                                .order(1)
                                .level("H1")
                                .build(),
                        DocumentationSection.builder()
                                .id("rest-setup")
                                .title("Project Setup")
                                .content("""
                                        ## Project Setup

                                        Start by creating a new Spring Boot project with the following dependencies:

                                        ```xml
                                        <dependencies>
                                            <dependency>
                                                <groupId>org.springframework.boot</groupId>
                                                <artifactId>spring-boot-starter-web</artifactId>
                                            </dependency>
                                            <dependency>
                                                <groupId>org.springframework.boot</groupId>
                                                <artifactId>spring-boot-starter-validation</artifactId>
                                            </dependency>
                                        </dependencies>
                                        ```

                                        ### Application Properties

                                        ```yaml
                                        server:
                                          port: 8080
                                        spring:
                                          application:
                                            name: rest-api-service
                                        ```
                                        """)
                                .order(2)
                                .level("H2")
                                .build(),
                        DocumentationSection.builder()
                                .id("rest-controller")
                                .title("Creating REST Controllers")
                                .content("""
                                        ## Creating REST Controllers

                                        ```java
                                        @RestController
                                        @RequestMapping("/api/v1/users")
                                        public class UserController {

                                            @Autowired
                                            private UserService userService;

                                            @GetMapping("/{id}")
                                            public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
                                                return userService.findById(id)
                                                    .map(user -> ResponseEntity.ok(UserDTO.fromEntity(user)))
                                                    .orElse(ResponseEntity.notFound().build());
                                            }

                                            @PostMapping
                                            public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO userDTO) {
                                                User user = userService.create(User.fromDTO(userDTO));
                                                return ResponseEntity.status(HttpStatus.CREATED)
                                                    .body(UserDTO.fromEntity(user));
                                            }
                                        }
                                        ```

                                        ### Key Annotations

                                        - `@RestController`: Marks the class as a REST controller
                                        - `@RequestMapping`: Maps HTTP requests to handler methods
                                        - `@GetMapping`, `@PostMapping`, etc.: HTTP method specific mappings
                                        - `@PathVariable`: Extracts values from the URI path
                                        - `@RequestBody`: Binds the HTTP request body to a transfer object
                                        """)
                                .order(3)
                                .level("H2")
                                .build()
                ))
                .metadata(DocumentationMetadata.builder()
                        .difficulty("INTERMEDIATE")
                        .estimatedReadTime(25)
                        .prerequisites(new String[]{"Java Basics", "Maven", "Spring Boot Fundamentals"})
                        .relatedTopics(new String[]{"HTTP Methods", "JSON", "API Design", "Testing"})
                        .sourceUrl("https://github.com/supernova/integration-docs")
                        .isOfficial(true)
                        .license("MIT")
                        .lastReviewed("2024-01-10")
                        .nextReviewDate("2024-07-10")
                        .build())
                .build();
    }

    private Documentation createKafkaDocumentation() {
        return Documentation.builder()
                .id("doc-kafka-001")
                .title("Apache Kafka Integration Guide")
                .description("Complete guide to event-driven architecture with Apache Kafka and Spring Boot")
                .integrationType("KAFKA")
                .category("Message Queue")
                .version("1.0.0")
                .author("Supernova Documentation Team")
                .createdAt(LocalDateTime.now().minusDays(25))
                .updatedAt(LocalDateTime.now().minusDays(3))
                .tags(Arrays.asList("kafka", "event-driven", "messaging", "streaming"))
                .contentType("MARKDOWN")
                .sections(Arrays.asList(
                        DocumentationSection.builder()
                                .id("kafka-intro")
                                .title("Introduction")
                                .content("""
                                        # Apache Kafka Integration Guide

                                        Learn how to implement event-driven architecture using Apache Kafka with Spring Boot.

                                        ## What You'll Learn

                                        - Kafka fundamentals and architecture
                                        - Setting up Kafka with Spring Boot
                                        - Creating producers and consumers
                                        - Error handling and retries
                                        - Monitoring and troubleshooting
                                        """)
                                .order(1)
                                .level("H1")
                                .build(),
                        DocumentationSection.builder()
                                .id("kafka-setup")
                                .title("Environment Setup")
                                .content("""
                                        ## Environment Setup

                                        ### Docker Compose for Local Development

                                        ```yaml
                                        version: '3.8'
                                        services:
                                          zookeeper:
                                            image: confluentinc/cp-zookeeper:latest
                                            environment:
                                              ZOOKEEPER_CLIENT_PORT: 2181

                                          kafka:
                                            image: confluentinc/cp-kafka:latest
                                            depends_on:
                                              - zookeeper
                                            ports:
                                              - "9092:9092"
                                            environment:
                                              KAFKA_BROKER_ID: 1
                                              KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
                                              KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
                                        ```
                                        """)
                                .order(2)
                                .level("H2")
                                .build(),
                        DocumentationSection.builder()
                                .id("kafka-producer")
                                .title("Creating Kafka Producers")
                                .content("""
                                        ## Creating Kafka Producers

                                        ```java
                                        @Service
                                        public class EventPublisher {

                                            @Autowired
                                            private KafkaTemplate<String, Object> kafkaTemplate;

                                            public void publishUserEvent(String topic, UserEvent event) {
                                                kafkaTemplate.send(topic, event)
                                                    .addCallback(
                                                        result -> log.info("Message sent to topic: {}", topic),
                                                        ex -> log.error("Failed to send message", ex)
                                                    );
                                            }
                                        }
                                        ```

                                        ### Configuration

                                        ```yaml
                                        spring:
                                          kafka:
                                            bootstrap-servers: localhost:9092
                                            producer:
                                              key-serializer: org.apache.kafka.common.serialization.StringSerializer
                                              value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
                                        ```
                                        """)
                                .order(3)
                                .level("H2")
                                .build()
                ))
                .metadata(DocumentationMetadata.builder()
                        .difficulty("ADVANCED")
                        .estimatedReadTime(35)
                        .prerequisites(new String[]{"Java Basics", "Maven", "Spring Boot", "Docker"})
                        .relatedTopics(new String[]{"Event-Driven Architecture", "Message Queues", "Distributed Systems"})
                        .sourceUrl("https://github.com/supernova/integration-docs")
                        .isOfficial(true)
                        .license("MIT")
                        .lastReviewed("2024-01-12")
                        .nextReviewDate("2024-07-12")
                        .build())
                .build();
    }

    private Documentation createSoapDocumentation() {
        return Documentation.builder()
                .id("doc-soap-001")
                .title("SOAP Web Services Guide")
                .description("Complete guide to building SOAP web services with Spring Boot")
                .integrationType("SOAP")
                .category("Web Services")
                .version("1.0.0")
                .author("Supernova Documentation Team")
                .createdAt(LocalDateTime.now().minusDays(20))
                .updatedAt(LocalDateTime.now().minusDays(7))
                .tags(Arrays.asList("soap", "webservice", "xml", "jaxb"))
                .contentType("MARKDOWN")
                .sections(Arrays.asList(
                        DocumentationSection.builder()
                                .id("soap-intro")
                                .title("Introduction")
                                .content("""
                                        # SOAP Web Services Guide

                                        Learn how to create SOAP web services using Spring Boot and JAXB.

                                        ## What You'll Learn

                                        - SOAP fundamentals and WSDL
                                        - Setting up Spring Boot SOAP services
                                        - Creating endpoints and payloads
                                        - JAXB marshalling/unmarshalling
                                        - Error handling and security
                                        """)
                                .order(1)
                                .level("H1")
                                .build(),
                        DocumentationSection.builder()
                                .id("soap-setup")
                                .title("Project Setup")
                                .content("""
                                        ## Project Setup

                                        Add the following dependency to your Spring Boot project:

                                        ```xml
                                        <dependency>
                                            <groupId>org.springframework.boot</groupId>
                                            <artifactId>spring-boot-starter-web-services</artifactId>
                                        </dependency>
                                        ```

                                        ### WSDL Configuration

                                        ```yaml
                                        spring:
                                          webservices:
                                            path: /ws
                                        ```
                                        """)
                                .order(2)
                                .level("H2")
                                .build(),
                        DocumentationSection.builder()
                                .id("soap-endpoint")
                                .title("Creating SOAP Endpoints")
                                .content("""
                                        ## Creating SOAP Endpoints

                                        ```java
                                        @Endpoint
                                        public class UserEndpoint {
                                            private static final String NAMESPACE_URI = "http://supernova.com/users";

                                            @Autowired
                                            private UserService userService;

                                            @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserRequest")
                                            @ResponsePayload
                                            public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
                                                GetUserResponse response = new GetUserResponse();
                                                User user = userService.findById(request.getId());
                                                if (user != null) {
                                                    response.setUser(user);
                                                }
                                                return response;
                                            }
                                        }
                                        ```

                                        ### JAXB Entities

                                        ```java
                                        @XmlRootElement(name = "GetUserRequest", namespace = "http://supernova.com/users")
                                        public class GetUserRequest {
                                            private Long id;

                                            // getters and setters
                                        }
                                        ```
                                        """)
                                .order(3)
                                .level("H2")
                                .build()
                ))
                .metadata(DocumentationMetadata.builder()
                        .difficulty("ADVANCED")
                        .estimatedReadTime(40)
                        .prerequisites(new String[]{"Java Basics", "Maven", "Spring Boot", "XML Schema"})
                        .relatedTopics(new String[]{"WSDL", "XML", "JAXB", "Web Services"})
                        .sourceUrl("https://github.com/supernova/integration-docs")
                        .isOfficial(true)
                        .license("MIT")
                        .lastReviewed("2024-01-08")
                        .nextReviewDate("2024-07-08")
                        .build())
                .build();
    }

    private Documentation createDatabaseDocumentation() {
        return Documentation.builder()
                .id("doc-db-001")
                .title("Database Integration Guide")
                .description("Complete guide to database integration with Spring Data JPA")
                .integrationType("DATABASE")
                .category("Data Access")
                .version("1.0.0")
                .author("Supernova Documentation Team")
                .createdAt(LocalDateTime.now().minusDays(35))
                .updatedAt(LocalDateTime.now().minusDays(2))
                .tags(Arrays.asList("database", "jpa", "hibernate", "postgresql"))
                .contentType("MARKDOWN")
                .sections(Arrays.asList(
                        DocumentationSection.builder()
                                .id("db-intro")
                                .title("Introduction")
                                .content("""
                                        # Database Integration Guide

                                        Learn how to integrate databases with Spring Boot using Spring Data JPA.

                                        ## What You'll Learn

                                        - Setting up database connections
                                        - Entity mapping with JPA
                                        - Repository patterns
                                        - Query methods and custom queries
                                        - Transaction management
                                        """)
                                .order(1)
                                .level("H1")
                                .build(),
                        DocumentationSection.builder()
                                .id("db-setup")
                                .title("Database Setup")
                                .content("""
                                        ## Database Setup

                                        ### PostgreSQL Configuration

                                        ```yaml
                                        spring:
                                          datasource:
                                            url: jdbc:postgresql://localhost:5432/mydb
                                            username: myuser
                                            password: mypassword
                                            driver-class-name: org.postgresql.Driver

                                          jpa:
                                            hibernate:
                                              ddl-auto: update
                                            show-sql: false
                                        ```

                                        ### Dependencies

                                        ```xml
                                        <dependency>
                                            <groupId>org.springframework.boot</groupId>
                                            <artifactId>spring-boot-starter-data-jpa</artifactId>
                                        </dependency>
                                        <dependency>
                                            <groupId>org.postgresql</groupId>
                                            <artifactId>postgresql</artifactId>
                                        </dependency>
                                        ```
                                        """)
                                .order(2)
                                .level("H2")
                                .build(),
                        DocumentationSection.builder()
                                .id("db-entities")
                                .title("Creating JPA Entities")
                                .content("""
                                        ## Creating JPA Entities

                                        ```java
                                        @Entity
                                        @Table(name = "users")
                                        public class User {
                                            @Id
                                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                                            private Long id;

                                            @Column(nullable = false, unique = true)
                                            private String email;

                                            @Column(name = "created_at")
                                            private LocalDateTime createdAt;

                                            // constructors, getters, setters
                                        }
                                        ```

                                        ### Repository Interface

                                        ```java
                                        @Repository
                                        public interface UserRepository extends JpaRepository<User, Long> {
                                            Optional<User> findByEmail(String email);
                                            List<User> findByEmailContainingIgnoreCase(String email);
                                        }
                                        ```
                                        """)
                                .order(3)
                                .level("H2")
                                .build()
                ))
                .metadata(DocumentationMetadata.builder()
                        .difficulty("INTERMEDIATE")
                        .estimatedReadTime(30)
                        .prerequisites(new String[]{"Java Basics", "Maven", "Spring Boot", "SQL Basics"})
                        .relatedTopics(new String[]{"JPA", "Hibernate", "Database Design", "SQL"})
                        .sourceUrl("https://github.com/supernova/integration-docs")
                        .isOfficial(true)
                        .license("MIT")
                        .lastReviewed("2024-01-13")
                        .nextReviewDate("2024-07-13")
                        .build())
                .build();
    }
}