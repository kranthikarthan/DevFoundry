# Node.js + Kafka Example

Simple producer/consumer using `kafkajs`.

## Run
```bash
docker compose -f ../springboot-kafka/docker-compose.kafka.yml up -d
npm install
npm start
```

Environment variables:
- `KAFKA_BOOTSTRAP_SERVERS` (default `localhost:9092`)
- `TOPIC` (default `devportal.example`)
