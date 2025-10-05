package com.supernova.integration.integration.service;

import com.supernova.integration.integration.dto.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class IntegrationTemplateServiceImpl implements IntegrationTemplateService {

    @Override
    public List<IntegrationTemplate> getAllTemplates() {
        return Arrays.asList(
                createRestTemplate(),
                createKafkaTemplate(),
                createSoapTemplate(),
                createDatabaseTemplate()
        );
    }

    @Override
    public List<IntegrationTemplate> getTemplatesByType(IntegrationType type) {
        return getAllTemplates().stream()
                .filter(template -> template.getType().equals(type))
                .toList();
    }

    @Override
    public Optional<IntegrationTemplate> getTemplateById(String id) {
        return getAllTemplates().stream()
                .filter(template -> template.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<IntegrationTemplate> getTemplateByTypeAndId(IntegrationType type, String id) {
        return getTemplatesByType(type).stream()
                .filter(template -> template.getId().equals(id))
                .findFirst();
    }

    private IntegrationTemplate createRestTemplate() {
        return IntegrationTemplate.builder()
                .id("rest-spring-boot")
                .name("Spring Boot REST API")
                .description("Production-ready REST API integration with Spring Boot")
                .type(IntegrationType.REST)
                .category("Web Services")
                .language("Java")
                .framework("Spring Boot")
                .version("3.2.0")
                .author("Supernova Team")
                .createdAt(LocalDateTime.now().minusDays(30))
                .updatedAt(LocalDateTime.now())
                .tags(Arrays.asList("spring-boot", "rest", "api", "production"))
                .metadata(TemplateMetadata.builder()
                        .difficulty("INTERMEDIATE")
                        .estimatedSetupTime(45)
                        .prerequisites("Java 17, Maven")
                        .requiredDependencies(new String[]{"spring-boot-starter-web", "spring-boot-starter-actuator"})
                        .optionalDependencies(new String[]{"spring-boot-starter-security"})
                        .documentationUrl("https://docs.spring.io/spring-boot/docs/current/reference/html/")
                        .sourceUrl("https://github.com/supernova/integration-templates")
                        .isProductionReady(true)
                        .license("MIT")
                        .build())
                .codeTemplate("""
                        @RestController
                        @RequestMapping("/api/v1")
                        public class UserController {

                            @Autowired
                            private UserService userService;

                            @GetMapping("/users/{id}")
                            public ResponseEntity<User> getUser(@PathVariable Long id) {
                                return userService.findById(id)
                                    .map(user -> ResponseEntity.ok(user))
                                    .orElse(ResponseEntity.notFound().build());
                            }

                            @PostMapping("/users")
                            public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
                                return ResponseEntity.status(HttpStatus.CREATED)
                                    .body(userService.save(user));
                            }
                        }
                        """)
                .configurationExample("""
                        server:
                          port: 8080
                          servlet:
                            context-path: /api/v1

                        spring:
                          datasource:
                            url: jdbc:h2:mem:testdb
                            driver-class-name: org.h2.Driver
                            username: sa
                            password: password

                          jpa:
                            hibernate:
                              ddl-auto: update
                            show-sql: true

                          application:
                            name: user-service
                        """)
                .usageExample("""
                        # Get user by ID
                        curl http://localhost:8080/api/v1/users/1

                        # Create new user
                        curl -X POST http://localhost:8080/api/v1/users \\
                          -H "Content-Type: application/json" \\
                          -d '{"name":"John Doe","email":"john@example.com"}'
                        """)
                .performanceMetrics(PerformanceMetrics.builder()
                        .averageResponseTime(150.0)
                        .throughput(1000.0)
                        .memoryUsage(256.0)
                        .cpuUsage(15.0)
                        .concurrentUsers(500)
                        .performanceGrade("A")
                        .lastBenchmarked("2024-01-15")
                        .build())
                .reliabilityMetrics(ReliabilityMetrics.builder()
                        .uptime(99.9)
                        .availability(99.95)
                        .errorRate(0.1)
                        .meanTimeBetweenFailures(720)
                        .meanTimeToRecovery(5)
                        .reliabilityGrade("A")
                        .lastIncident("2023-11-20")
                        .monitoringUrl("https://grafana.example.com/d/user-service")
                        .build())
                .build();
    }

    private IntegrationTemplate createKafkaTemplate() {
        return IntegrationTemplate.builder()
                .id("kafka-spring-boot")
                .name("Spring Boot Kafka Integration")
                .description("Event-driven architecture with Apache Kafka and Spring Boot")
                .type(IntegrationType.KAFKA)
                .category("Message Queue")
                .language("Java")
                .framework("Spring Boot")
                .version("3.2.0")
                .author("Supernova Team")
                .createdAt(LocalDateTime.now().minusDays(20))
                .updatedAt(LocalDateTime.now())
                .tags(Arrays.asList("kafka", "spring-boot", "event-driven", "streaming"))
                .metadata(TemplateMetadata.builder()
                        .difficulty("ADVANCED")
                        .estimatedSetupTime(90)
                        .prerequisites("Java 17, Maven, Docker, Kafka")
                        .requiredDependencies(new String[]{"spring-kafka", "spring-boot-starter-web"})
                        .optionalDependencies(new String[]{"kafka-streams"})
                        .documentationUrl("https://docs.spring.io/spring-kafka/docs/current/reference/html/")
                        .sourceUrl("https://github.com/supernova/integration-templates")
                        .isProductionReady(true)
                        .license("MIT")
                        .build())
                .codeTemplate("""
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

                        @KafkaListener(topics = "user-events", groupId = "user-service")
                        public void handleUserEvent(UserEvent event) {
                            log.info("Received user event: {}", event);
                            // Process the event
                        }
                        """)
                .configurationExample("""
                        spring:
                          kafka:
                            bootstrap-servers: localhost:9092
                            producer:
                              key-serializer: org.apache.kafka.common.serialization.StringSerializer
                              value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
                            consumer:
                              group-id: user-service
                              auto-offset-reset: earliest
                              key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
                              value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
                              properties:
                                spring.json.trusted.packages: "com.supernova.integration"
                        """)
                .usageExample("""
                        # Start Kafka
                        docker run -p 9092:9092 confluentinc/cp-kafka:latest

                        # Send test message
                        kafka-console-producer --broker-list localhost:9092 --topic user-events \\
                          --property "key.separator=:" --property "parse.key=true"

                        # Consume messages
                        kafka-console-consumer --bootstrap-server localhost:9092 \\
                          --topic user-events --from-beginning
                        """)
                .performanceMetrics(PerformanceMetrics.builder()
                        .averageResponseTime(50.0)
                        .throughput(50000.0)
                        .memoryUsage(512.0)
                        .cpuUsage(25.0)
                        .concurrentUsers(1000)
                        .performanceGrade("A")
                        .lastBenchmarked("2024-01-10")
                        .build())
                .reliabilityMetrics(ReliabilityMetrics.builder()
                        .uptime(99.95)
                        .availability(99.9)
                        .errorRate(0.05)
                        .meanTimeBetweenFailures(1440)
                        .meanTimeToRecovery(2)
                        .reliabilityGrade("A")
                        .lastIncident("2023-12-15")
                        .monitoringUrl("https://grafana.example.com/d/kafka-metrics")
                        .build())
                .build();
    }

    private IntegrationTemplate createSoapTemplate() {
        return IntegrationTemplate.builder()
                .id("soap-spring-boot")
                .name("Spring Boot SOAP Web Service")
                .description("SOAP web service integration with Spring Boot and JAXB")
                .type(IntegrationType.SOAP)
                .category("Web Services")
                .language("Java")
                .framework("Spring Boot")
                .version("3.2.0")
                .author("Supernova Team")
                .createdAt(LocalDateTime.now().minusDays(15))
                .updatedAt(LocalDateTime.now())
                .tags(Arrays.asList("soap", "spring-boot", "webservice", "jaxb"))
                .metadata(TemplateMetadata.builder()
                        .difficulty("ADVANCED")
                        .estimatedSetupTime(120)
                        .prerequisites("Java 17, Maven, WSDL knowledge")
                        .requiredDependencies(new String[]{"spring-boot-starter-web-services", "jaxb-api"})
                        .optionalDependencies(new String[]{"cxf-spring-boot-starter"})
                        .documentationUrl("https://docs.spring.io/spring-ws/docs/current/reference/html/")
                        .sourceUrl("https://github.com/supernova/integration-templates")
                        .isProductionReady(true)
                        .license("MIT")
                        .build())
                .codeTemplate("""
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
                        """)
                .configurationExample("""
                        spring:
                          webservices:
                            path: /ws
                            servlet:
                              init:
                                transformWsdlLocations: true

                        users:
                          wsdl-location: classpath:/wsdl/users.wsdl
                        """)
                .usageExample("""
                        # Generate client from WSDL
                        wsimport -keep -p com.supernova.client http://localhost:8080/ws/users.wsdl

                        # Test SOAP request
                        curl -X POST http://localhost:8080/ws \\
                          -H "Content-Type: text/xml; charset=utf-8" \\
                          -H "SOAPAction: GetUser" \\
                          -d @get-user-request.xml
                        """)
                .performanceMetrics(PerformanceMetrics.builder()
                        .averageResponseTime(300.0)
                        .throughput(500.0)
                        .memoryUsage(384.0)
                        .cpuUsage(20.0)
                        .concurrentUsers(200)
                        .performanceGrade("B")
                        .lastBenchmarked("2024-01-05")
                        .build())
                .reliabilityMetrics(ReliabilityMetrics.builder()
                        .uptime(99.8)
                        .availability(99.85)
                        .errorRate(0.2)
                        .meanTimeBetweenFailures(480)
                        .meanTimeToRecovery(10)
                        .reliabilityGrade("B")
                        .lastIncident("2023-12-01")
                        .monitoringUrl("https://grafana.example.com/d/soap-services")
                        .build())
                .build();
    }

    private IntegrationTemplate createDatabaseTemplate() {
        return IntegrationTemplate.builder()
                .id("database-spring-data")
                .name("Spring Data JPA Database Integration")
                .description("Database integration with Spring Data JPA and PostgreSQL")
                .type(IntegrationType.DATABASE)
                .category("Data Access")
                .language("Java")
                .framework("Spring Boot")
                .version("3.2.0")
                .author("Supernova Team")
                .createdAt(LocalDateTime.now().minusDays(25))
                .updatedAt(LocalDateTime.now())
                .tags(Arrays.asList("spring-data", "jpa", "postgresql", "database"))
                .metadata(TemplateMetadata.builder()
                        .difficulty("INTERMEDIATE")
                        .estimatedSetupTime(60)
                        .prerequisites("Java 17, Maven, PostgreSQL")
                        .requiredDependencies(new String[]{"spring-boot-starter-data-jpa", "postgresql"})
                        .optionalDependencies(new String[]{"flyway-core", "liquibase-core"})
                        .documentationUrl("https://docs.spring.io/spring-data/jpa/docs/current/reference/html/")
                        .sourceUrl("https://github.com/supernova/integration-templates")
                        .isProductionReady(true)
                        .license("MIT")
                        .build())
                .codeTemplate("""
                        @Entity
                        @Table(name = "users")
                        public class User {
                            @Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            private Long id;

                            @Column(nullable = false)
                            private String name;

                            @Column(nullable = false, unique = true)
                            private String email;

                            @Column(name = "created_at")
                            private LocalDateTime createdAt;

                            // Constructors, getters, setters
                        }

                        @Repository
                        public interface UserRepository extends JpaRepository<User, Long> {
                            Optional<User> findByEmail(String email);
                            List<User> findByNameContainingIgnoreCase(String name);
                        }

                        @Service
                        @Transactional
                        public class UserService {
                            @Autowired
                            private UserRepository userRepository;

                            public Optional<User> findByEmail(String email) {
                                return userRepository.findByEmail(email);
                            }

                            public User save(User user) {
                                user.setCreatedAt(LocalDateTime.now());
                                return userRepository.save(user);
                            }
                        }
                        """)
                .configurationExample("""
                        spring:
                          datasource:
                            url: jdbc:postgresql://localhost:5432/userdb
                            username: user_service
                            password: ${DB_PASSWORD}
                            driver-class-name: org.postgresql.Driver

                          jpa:
                            hibernate:
                              ddl-auto: validate
                            show-sql: false
                            properties:
                              hibernate:
                                dialect: org.hibernate.dialect.PostgreSQLDialect
                                format_sql: true

                          flyway:
                            enabled: true
                            locations: classpath:db/migration

                        logging:
                          level:
                            org.hibernate.SQL: DEBUG
                            org.hibernate.type.descriptor.sql.BasicBinder: TRACE
                        """)
                .usageExample("""
                        # Run PostgreSQL
                        docker run -p 5432:5432 -e POSTGRES_DB=userdb \\
                          -e POSTGRES_USER=user_service -e POSTGRES_PASSWORD=password \\
                          postgres:15

                        # Run migrations
                        mvn flyway:migrate

                        # Test connection
                        psql -h localhost -p 5432 -U user_service -d userdb
                        """)
                .performanceMetrics(PerformanceMetrics.builder()
                        .averageResponseTime(100.0)
                        .throughput(800.0)
                        .memoryUsage(320.0)
                        .cpuUsage(18.0)
                        .concurrentUsers(300)
                        .performanceGrade("A")
                        .lastBenchmarked("2024-01-12")
                        .build())
                .reliabilityMetrics(ReliabilityMetrics.builder()
                        .uptime(99.95)
                        .availability(99.9)
                        .errorRate(0.05)
                        .meanTimeBetweenFailures(1440)
                        .meanTimeToRecovery(3)
                        .reliabilityGrade("A")
                        .lastIncident("2023-11-30")
                        .monitoringUrl("https://grafana.example.com/d/database-metrics")
                        .build())
                .build();
    }
}