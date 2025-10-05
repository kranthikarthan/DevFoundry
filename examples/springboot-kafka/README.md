# Spring Boot + Kafka Example

This example exposes a simple `/api/messages` endpoint that publishes to a Kafka topic, and a `@KafkaListener` that logs consumed messages.

## Prereqs
- Java 17
- Maven 3.9+

## Build and Run (local Kafka via Docker Compose at repo root)
```bash
# In repo root, start Kafka (see compose below)
docker compose -f examples/springboot-kafka/docker-compose.kafka.yml up -d

# In this example folder
mvn -q -DskipTests package
java -jar target/springboot-kafka-example-0.1.0.jar
```

Send a message:
```bash
curl -X POST http://localhost:8081/api/messages -H 'Content-Type: text/plain' -d 'hello from devportal'
```

## Docker
```bash
mvn -q -DskipTests package
docker build -t springboot-kafka-example .
docker run --rm -e KAFKA_BOOTSTRAP_SERVERS=host.docker.internal:9092 -p 8081:8081 springboot-kafka-example
```
