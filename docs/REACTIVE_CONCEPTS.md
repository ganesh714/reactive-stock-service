# Reactive Concepts (Interview-Focused)

This document summarizes key Reactor and reactive-system concepts interviewer will probe for. It explains why we made certain design choices in this project and points to the Reactor operators and testing tools to use in production-quality systems.

## 1. Reactive Error Handling (Crucial for Interviews)

- The concept: reactive code executes asynchronously and potentially on different threads. Traditional `try-catch` is ineffective across asynchronous boundaries because errors travel as signals through the stream, not as synchronous exceptions.
- Implementation (how we handle 404 in `getStockPrice`): the service returns `Mono.empty()` when the entity is not present. The controller maps that empty signal to a 404:

```java
return stockService.getStockPrice(symbol)
    .map(price -> ResponseEntity.ok(price))
    .defaultIfEmpty(ResponseEntity.notFound().build());
```

- Operators interviewers expect you to know: `onErrorResume()`, `onErrorReturn()`, `onErrorMap()`, `switchIfEmpty()`, and `defaultIfEmpty()`.

## 2. Backpressure (Core of the Reactive Manifesto)

- The concept: backpressure is the ability for the consumer to signal the producer to slow down when it cannot keep up with the rate of data.
- In this project: the SSE stream is emitted at `1 event / 2s` to keep things simple and predictable. In a production-grade ticker (thousands of ticks/sec) Reactor's backpressure model is critical.
- Common Reactor operators to control backpressure: `.onBackpressureDrop()`, `.onBackpressureBuffer()`, `.onBackpressureLatest()` — choose the strategy based on whether you can lose events, buffer them, or only keep the latest.

## 3. Server-Sent Events (SSE) vs WebSockets

- SSE is unidirectional (Server -> Client) and fits a read-only stream like a stock ticker. It uses normal HTTP connections, is simpler to implement, and generally easier to route through proxies and load balancers.
- WebSockets are bidirectional — useful when the client must push frequent events back to server (chat, collaborative apps). For a typical ticker, WebSocket adds unnecessary complexity and resource cost.
- Documented answer: choose SSE when the server primarily pushes updates and client interactions are minimal.

## 4. Cold vs Hot Publishers

- Cold publishers: nothing happens until subscription; each subscriber gets an independent sequence. `Flux.interval(...)` is a cold publisher by default — each client gets its own timer and its own copy of emitted values from subscription time.
- Hot publishers: emit regardless of subscribers (e.g., a central market data feed). Late subscribers miss already-emitted events.
- Why it matters: choose cold for independent, per-client streams; choose hot for centralized broadcast of live data.

## 5. Testing Reactive Streams (`StepVerifier`)

- Avoid `Thread.sleep()` in tests. Use `StepVerifier` to assert emitted signals deterministically.
- For time-based streams, use `StepVerifier.withVirtualTime()` to compress time and assert long-running sequences quickly. Example:

```java
StepVerifier.withVirtualTime(() -> service.priceFlux())
    .thenAwait(Duration.ofHours(1))
    .expectNextCount(30)
    .verifyComplete();
```

- `StepVerifier` lets you assert values, order, completion, and error signals precisely.

## 6. Health Checks & Actuator

- The project includes `spring-boot-starter-actuator` to expose health and readiness endpoints (e.g., `/actuator/health`).
- In containerized deployments and `docker-compose`, the health endpoint lets orchestrators verify the Netty/non-blocking server is healthy and ready to accept traffic.

---

If you want, I can insert a short cross-reference into `docs/OVERVIEW.md` or `docs/ARCHITECTURE.md` pointing to this file.
