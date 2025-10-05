# Developer Integration Portal

A full-stack system that provides developers with copyable integration code templates, performance insights, and reliability metrics for popular integrations (REST, Kafka, SOAP, DB, File, Cloud).

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)
![React](https://img.shields.io/badge/React-18.2.0-blue)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)
![CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub%20Actions-yellow)

## 🚀 Features

### Backend Services (Spring Boot 3.x)
- **Integration Service**: Serves integration template metadata and code examples
- **Performance Service**: Provides performance metrics and benchmarking data
- **Documentation Service**: Serves comprehensive Markdown documentation
- **Metrics Service**: Exposes Prometheus metrics for monitoring

### Frontend (React + TypeScript)
- Modern, responsive UI built with Material-UI
- Integration template browser with syntax highlighting
- Performance dashboards and metrics visualization
- Comprehensive documentation viewer
- Real-time metrics simulation

### Observability & Monitoring
- **Prometheus**: Metrics collection and alerting
- **Grafana**: Visualization and dashboards
- Custom business metrics for integration usage

### DevOps & Deployment
- Docker containerization for all services
- Docker Compose for local development
- GitHub Actions CI/CD pipeline
- Production-ready configuration

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   React Frontend│    │   Integration   │    │   Performance   │
│     (Port 80)   │◄──►│   Service       │◄──►│   Service       │
│                 │    │   (Port 8081)   │    │   (Port 8082)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                        │                     │
         ▼                        ▼                     ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Documentation │    │   Metrics       │    │   PostgreSQL    │
│   Service       │    │   Service       │    │   Database      │
│   (Port 8083)   │    │   (Port 8084)   │    │   (Port 5432)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                        │                     │
         ▼                        ▼                     ▼
┌─────────────────┐    ┌─────────────────┐
│   Prometheus    │    │   Grafana       │
│   (Port 9090)   │    │   (Port 3000)   │
└─────────────────┘    └─────────────────┘
```

## 🛠️ Technology Stack

### Backend
- **Java 17** - Latest LTS version
- **Spring Boot 3.2.0** - Latest Spring Boot framework
- **Spring Web** - REST API development
- **Spring Actuator** - Health checks and metrics
- **Micrometer + Prometheus** - Metrics collection
- **OpenAPI 3.0** - API documentation
- **Lombok** - Code generation
- **MapStruct** - DTO mapping
- **JUnit 5 + Testcontainers** - Testing

### Frontend
- **React 18.2.0** - Modern React framework
- **TypeScript** - Type safety
- **Material-UI** - Component library
- **React Router** - Client-side routing
- **Axios** - HTTP client
- **React Syntax Highlighter** - Code highlighting
- **React Markdown** - Markdown rendering

### DevOps & Monitoring
- **Docker** - Containerization
- **Docker Compose** - Local orchestration
- **Prometheus** - Metrics collection
- **Grafana** - Visualization
- **GitHub Actions** - CI/CD pipeline
- **PostgreSQL** - Database

## 🚀 Quick Start

### Prerequisites
- Docker and Docker Compose
- Java 17 (for local development)
- Node.js 18+ (for frontend development)

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd developer-integration-portal
   ```

2. **Start all services with Docker Compose**
   ```bash
   docker-compose up -d
   ```

3. **Verify services are running**
   ```bash
   # Check service health
   curl http://localhost:8081/actuator/health
   curl http://localhost:8082/actuator/health
   curl http://localhost:8083/actuator/health
   curl http://localhost:8084/actuator/health

   # Access Prometheus
   open http://localhost:9090

   # Access Grafana (admin/admin)
   open http://localhost:3000
   ```

4. **Access the frontend**
   ```bash
   open http://localhost:80
   ```

### Manual Development Setup

1. **Start PostgreSQL database**
   ```bash
   docker run -p 5432:5432 -e POSTGRES_DB=integration_portal \
     -e POSTGRES_USER=portal_user -e POSTGRES_PASSWORD=portal_password \
     postgres:15-alpine
   ```

2. **Build and run backend services**
   ```bash
   # Build all services
   mvn clean package

   # Run integration service
   java -jar integration-service/target/*.jar

   # Run performance service
   java -jar performance-service/target/*.jar

   # Run documentation service
   java -jar doc-service/target/*.jar

   # Run metrics service
   java -jar metrics-service/target/*.jar
   ```

3. **Install frontend dependencies and start**
   ```bash
   cd frontend
   npm install
   npm start
   ```

## 📊 Available Endpoints

### Integration Service (Port 8081)
- `GET /api/integrations` - Get all integration templates
- `GET /api/integrations/{type}` - Get templates by type
- `GET /api/integrations/{type}/{id}` - Get specific template
- `GET /actuator/health` - Health check
- `GET /actuator/prometheus` - Prometheus metrics

### Performance Service (Port 8082)
- `GET /api/performance` - Get all performance reports
- `GET /api/performance/{type}` - Get performance report by type
- `GET /api/performance/types` - Get available integration types

### Documentation Service (Port 8083)
- `GET /api/docs` - Get all documentation
- `GET /api/docs/{type}` - Get documentation by type
- `GET /api/docs/types` - Get available documentation types

### Metrics Service (Port 8084)
- `GET /api/metrics/simulate` - Simulate integration request
- `GET /api/metrics/simulate-download` - Simulate template download
- `GET /api/metrics/simulate-docs` - Simulate documentation view
- `GET /actuator/prometheus` - Prometheus metrics

## 🧪 Testing

### Run all tests
```bash
mvn clean verify
```

### Run tests for specific service
```bash
mvn test -pl integration-service
```

### Run integration tests
```bash
mvn verify -Pintegration-test
```

## 🔧 Configuration

### Environment Variables
- `SPRING_PROFILES_ACTIVE` - Spring profile (docker, test, prod)
- `DB_URL` - Database connection URL
- `DB_USERNAME` - Database username
- `DB_PASSWORD` - Database password

### Application Properties
Each service has its own `application.yml` file with configuration for:
- Server ports and context paths
- Database connections
- Monitoring and metrics
- Logging levels

## 📈 Monitoring

### Prometheus Metrics
- Custom business metrics (integration requests, template downloads, etc.)
- Spring Boot Actuator metrics
- JVM and system metrics

### Grafana Dashboards
- Integration portal overview dashboard
- Service health monitoring
- Performance metrics visualization

## 🚢 Deployment

### Docker Deployment
```bash
# Build and start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Scale services
docker-compose up -d --scale integration-service=2
```

### Kubernetes Deployment
```bash
# Apply Kubernetes manifests
kubectl apply -f k8s/

# Check deployment status
kubectl get pods,services,ingress
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Workflow
1. **Feature Development**: Create feature branch from `develop`
2. **Testing**: Run tests locally and ensure CI passes
3. **Code Review**: Create PR to `develop` branch
4. **Production Release**: Merge `develop` to `main` for production deployment

## 📝 API Documentation

### OpenAPI/Swagger
- Integration Service: http://localhost:8081/swagger-ui.html
- Performance Service: http://localhost:8082/swagger-ui.html
- Documentation Service: http://localhost:8083/swagger-ui.html

## 🐛 Troubleshooting

### Common Issues

1. **Port conflicts**: Ensure ports 8081-8084 and 80, 9090, 3000 are available
2. **Database connection**: Verify PostgreSQL is running and credentials are correct
3. **Frontend build**: Clear npm cache if build fails (`npm ci`)

### Logs
```bash
# View all service logs
docker-compose logs

# View specific service logs
docker-compose logs integration-service

# Follow logs in real-time
docker-compose logs -f frontend
```

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- React team for the powerful frontend library
- Prometheus and Grafana communities for monitoring tools
- All contributors and open source projects used

---

**Made with ❤️ for developers, by developers**