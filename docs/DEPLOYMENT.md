# Deployment & Build Guide

## Prerequisites
- **Java 17** or higher
- **Maven** (optional, wrapper provided)
- **Docker** and **Docker Compose**

## Local Build (without Docker)
The project includes a Maven wrapper, so you don't need a global Maven installation.

1. **Clean and Package:**
   ```bash
   ./mvnw clean package
   ```
2. **Run the Application:**
   ```bash
   java -jar target/reactive-stock-service-0.0.1-SNAPSHOT.jar
   ```

The application will be available at `http://localhost:8080`.

## Docker Deployment (Recommended)
This is the easiest way to run the application with all its configurations.

1. **Configure Environment:**
   Copy the example environment file:
   ```bash
   cp .env.example .env
   ```
2. **Build and Start:**
   ```bash
   docker-compose up --build -d
   ```

### Docker Services
- **app:** The Spring WebFlux application.
- **Port Mapping:** Host `8080` -> Container `8080`.
- **Health Check:** Monitors `http://localhost:8080/actuator/health` to ensure the service is ready.

## Environment Variables
Defined in `.env`:
| Variable | Description |
| :--- | :--- |
| `SERVER_PORT` | The port the application listens on (default: 8080). |

## Monitoring Logs
To view the output of the reactive logging filter:
```bash
docker logs -f reactive-stock-service
```
You should see entries like:
`Request: GET /api/stocks/AAPL, Duration: 15ms`
