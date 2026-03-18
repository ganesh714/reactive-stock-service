# Reactive Stock Price Service

A non-blocking, reactive RESTful service built with Spring WebFlux and Project Reactor to provide real-time stock price data via Server-Sent Events (SSE).

## Technologies Used
* Java 17+
* Spring Boot 3+ (Spring WebFlux)
* Project Reactor (Mono & Flux)
* Docker & Docker Compose

## Setup and Running the Application

1. **Build the Java Application:**
   First, compile the code and package it into a JAR file.
   `./mvnw clean package` (or `mvn clean package` if Maven is installed globally)

2. **Set up Environment Variables:**
   Copy the example environment file and create your local `.env`.
   `cp .env.example .env`

3. **Run with Docker Compose:**
   Build the Docker image and start the container.
   `docker-compose up --build`

## API Endpoints
* **Single Stock:** `GET /api/stocks/{symbol}` (e.g., /api/stocks/AAPL)
* **Multiple Stocks:** `GET /api/stocks?symbols={sym1,sym2}` (e.g., /api/stocks?symbols=AAPL,GOOG)
* **Live SSE Stream:** `GET /api/stocks/stream/{symbol}`