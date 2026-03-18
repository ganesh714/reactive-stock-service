# Reactive Stock Price Service

![Spring WebFlux](https://img.shields.io/badge/Spring--Boot-3.x-brightgreen)
![Project Reactor](https://img.shields.io/badge/Project--Reactor-Flux%2FMono-blue)
![Docker](https://img.shields.io/badge/Docker-Containerized-blue)

A high-concurrency, non-blocking stock price service built with **Spring WebFlux** and **Project Reactor**. This service provides real-time data using request-response patterns and live streaming via Server-Sent Events (SSE).

## 🚀 Key Features
- **Non-Blocking APIs:** Fully reactive endpoints for the best resource utilization.
- **Live Streaming:** Real-time stock price fluctuations via SSE.
- **Asynchronous Logging:** Custom `WebFilter` to track request metrics without blocking.
- **Containerized:** Ready to run anywhere with Docker Compose.

## 📚 Documentation
Explore the detailed guides below to understand the project better:

1.  **[Project Overview](docs/OVERVIEW.md)** - Why reactive? The problem and our solution.
2.  **[Architecture & Design](docs/ARCHITECTURE.md)** - High-level and Low-level design docs.
3.  **[API Reference](docs/API.md)** - How to use the endpoints.
4.  **[Deployment Guide](docs/DEPLOYMENT.md)** - How to build and run the service.

## 🛠️ Quick Start
1. Clone the repository.
2. Ensure Docker is running.
3. Run `docker-compose up --build -d`.
4. Access the API at `http://localhost:8080/api/stocks/AAPL`.

---
*Built for high performance and scalability.*