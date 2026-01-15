# DevOps Demo Application

A lightweight Spring Boot application designed to showcase CI/CD pipeline deployments to AWS ECS Fargate.

## Features

- Modern, responsive UI with dark theme
- REST API endpoints (`/api/info`, `/api/echo`, `/api/health`)
- Interactive API playground
- Docker-ready with health checks
- Spring Boot 3.2 with Java 17

## Quick Start

### Local Development

```bash
# Build the WAR
mvn clean package -DskipTests

# Run locally (embedded Tomcat)
mvn spring-boot:run

# Access at http://localhost:8080
```

### Docker Build

```bash
# Build WAR first
mvn clean package -DskipTests

# Build Docker image
docker build -t devops-demo:latest .

# Run container
docker run -p 8080:8080 devops-demo:latest
```

## Deployment Pipeline

### Prerequisites

1. **AWS Infrastructure** (via Terraform):
   ```bash
   cd ../terraform/ecs-fargate
   terraform init
   terraform apply
   ```

2. **GitHub Secrets**:
   - `AWS_ACCOUNT_ID` - Your AWS account ID
   - `NEXUS_USERNAME` (optional) - Nexus repository username
   - `NEXUS_PASSWORD` (optional) - Nexus repository password

3. **GitHub Variables** (optional):
   - `NEXUS_URL` - Nexus repository URL (enables Nexus deployment)

### Pipeline Steps

1. **Build**: Maven builds the WAR artifact
2. **Nexus** (optional): Deploy artifact to Nexus repository
3. **Docker**: Build and push image to Amazon ECR
4. **Deploy**: Update ECS Fargate service with new image

## API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/` | GET | Main UI dashboard |
| `/api/health` | GET | Health check endpoint |
| `/api/info` | GET | Application and system info |
| `/api/echo` | POST | Echo back JSON payload |

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `APP_VERSION` | 1.0.0 | Application version |
| `APP_ENVIRONMENT` | development | Environment name |

## Architecture

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  GitHub Actions │────▶│   Amazon ECR    │────▶│   ECS Fargate   │
│    (Build)      │     │   (Registry)    │     │   (Deploy)      │
└─────────────────┘     └─────────────────┘     └─────────────────┘
        │
        ▼
┌─────────────────┐
│     Nexus       │
│   (Optional)    │
└─────────────────┘
```

## License

MIT
