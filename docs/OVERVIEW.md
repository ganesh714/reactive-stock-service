# Project Overview: Reactive Stock Service

## The Problem
Traditional web frameworks like Spring MVC follow a **thread-per-request** model. In this model, each incoming HTTP request is assigned a dedicated thread from a thread pool. When the application performs a blocking I/O operation (e.g., querying a database or calling another service), the thread stays idle until the operation completes. 

Under high load—such as thousands of users requesting live stock prices simultaneously—this leads to:
- **Thread Exhaustion:** The server runs out of available threads.
- **High Resource Consumption:** Each thread consumes memory (stack space).
- **Latency Spikes:** Requests wait in a queue for a free thread, leading to poor performance and potential crashes.

## The Solution
The **Reactive Stock Service** is built using a non-blocking, event-driven model. Instead of threads waiting for data, the system uses a small number of threads to handle many concurrent connections asynchronously.

### Key Benefits
- **Non-Blocking I/O:** Threads are never held idle; they are immediately released back to the event loop while waiting for data.
- **High Concurrency:** Capable of handling thousands of simultaneous connections with minimal hardware resources.
- **Backpressure Support:** The service can signal to producers how much data it can handle, preventing overwhelming the consumer.
- **Real-time Streaming:** Built-in support for Server-Sent Events (SSE) to push live updates to clients efficiently.

## Objective
The goal of this project is to provide a robust, low-latency stock price service that demonstrates the power of Spring WebFlux and Project Reactor in building modern, high-concurrency systems.
