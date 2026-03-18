# Future Enhancements & Scalability

This document outlines practical, interview-ready options to evolve the project from a demonstration into a production-grade, horizontally-scalable reactive service.

## 1. Database Integration (non-blocking persistence)

- Problem: the current in-memory `Map` is only suitable for demos. Traditional JDBC (`java.sql.*`) is blocking and will tie up Netty event-loop threads, negating WebFlux benefits.
- Solution: use reactive drivers such as **R2DBC** for relational databases or reactive clients for NoSQL (e.g., Reactive MongoDB, Reactive Redis). With Spring Data R2DBC or Spring Data Reactive MongoDB, database calls remain non-blocking and integrate smoothly into the `Mono`/`Flux` pipelines.

## 2. Horizontal Scaling & Statelessness

- Problem: per-instance in-memory state prevents safe horizontal scaling (clients may hit different instances and see inconsistent data).
- Solution: keep services stateless and store shared state in external reactive stores (e.g., **Reactive Redis** via Spring Data Redis or a reactive DB). For broadcast scenarios, consider a central pub/sub (Redis Streams, Kafka) to fan-out market updates to all instances.

## 3. Consuming the SSE Stream (frontend perspective)

- How a browser connects: use the built-in `EventSource` API to subscribe to `text/event-stream`. Example (vanilla JS):

```js
const es = new EventSource('/api/stocks/stream');
es.onmessage = e => { const update = JSON.parse(e.data); /* update UI */ };
```

- In Angular: wrap `EventSource` with an `Observable` or use libraries that convert SSE to RxJS streams, enabling composition with other Observables.
- CORS: enable CORS in the WebFlux config when frontends are hosted on different origins so browsers can connect.

## 4. Load Testing & Proof of Concept

- To prove non-blocking benefits, run load tests (Gatling, JMeter, Vegeta) simulating many concurrent connections (e.g., 10k clients).
- What to measure: concurrent open connections, average latency, error rate, and CPU/memory use. Expect the Servlet/Tomcat thread-per-request model to exhaust threads under very high concurrency, while WebFlux/Netty typically sustains many more concurrent connections with a small thread pool.

## 5. Operational & Resilience Considerations

- Backpressure strategies: for high-frequency feeds, add `.onBackpressureDrop()` or `.onBackpressureBuffer()` to protect downstream consumers and avoid OOM.
- Circuit breakers & rate limiting: integrate resilience patterns (Resilience4j, rate-limiters) around upstream data sources.

## 6. Summary (how to prioritize)

- Short term: externalize state to a reactive store (Redis/R2DBC) and add CORS and frontend SSE docs.
- Medium term: introduce pub/sub for market data, backpressure policies, and resilience patterns.
- Long term: full-scale load testing and observability (tracing, metrics for reactive flows) before production rollout.
