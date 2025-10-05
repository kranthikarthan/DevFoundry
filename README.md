# Developer Integration Portal

An open-source grade monorepo featuring:
- `api`: Express + TypeScript API with OpenAPI docs
- `web`: Vite + React developer portal
- `packages/sdk`: TypeScript SDK to consume the API

## Quick start

Prereqs: Node 20+

```bash
npm install
npm run dev
```

- API: http://localhost:3000
- Web: http://localhost:5173
- API Docs: http://localhost:3000/docs

## Tests
```bash
npm test
```

## Docker
```bash
docker compose up --build
```

## Examples
- Spring Boot + Kafka: `examples/springboot-kafka`
- Node.js + Kafka: `examples/nodejs-kafka`

## Spring Templates
Production-ready Spring Boot templates under `templates/spring`:
- `common`: Observability, WebClient tuning, global error handling, OpenAPI
- `kafka`: Producer/consumer skeleton with sane defaults
- `postgres`: JPA + Flyway + Hikari pool tuned defaults
- `redis`: Simple caching service example
- `webhook`: Secure inbound webhooks with HMAC verification

See `docs/INTEGRATIONS.md` for performance and reliability notes.

## Contributing
See `CONTRIBUTING.md` and `CODE_OF_CONDUCT.md`.
