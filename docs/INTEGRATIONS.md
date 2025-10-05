# Integration Templates and Trade-offs

This repo includes Spring Boot templates covering common integration categories:
- Kafka (event streaming)
- Postgres (transactional database)
- Redis (cache, key/value)
- Webhooks (inbound HTTP)

## Kafka
- Performance tuning: partitions, `acks`, compression, batch.size, linger.ms
- Reliability: at-least-once with retries and DLQ; exactly-once needs transactions + idempotency
- Backpressure: consumer max.poll.interval.ms, max.poll.records

## Postgres
- Performance: connection pool sizing (Hikari), indexes, batching
- Reliability: transactions, migrations (Flyway), timeouts

## Redis
- Performance: pipelining, connection reuse
- Reliability: TTLs, eviction policies, replicas/sentinel for HA

## Webhooks
- Performance: enqueue for async processing; short timeouts
- Reliability: HMAC signature verification, idempotency keys, retries with exponential backoff
