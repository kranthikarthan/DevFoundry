# Contributing

Thanks for your interest in contributing! This repo is a monorepo with three packages:
- `api`: Express + TypeScript API with OpenAPI docs
- `web`: Vite + React web portal
- `packages/sdk`: TypeScript SDK

## Quick start
```bash
# Node 20+
nvm use 20 || echo "Make sure Node 20+ is installed"

# Install deps
npm install

# Run API and Web together (dev)
npm run dev

# Run tests
npm test
```

## Development
- Use feature branches and open PRs
- Write tests for new behavior
- Keep PRs focused and under ~300 lines when possible

## Commit messages
- Prefer Conventional Commits (e.g., `feat(api): add integrations list`)

## Security
Please report vulnerabilities privately via `security@example.com`.
