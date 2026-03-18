# API Reference

The Stock Price Service exposes three primary endpoints under the `/api` path.

## 1. Get Single Stock Price
Returns the current base price for a specific stock symbol.

- **URL:** `/api/stocks/{symbol}`
- **Method:** `GET`
- **Success Response:** `200 OK`
- **Error Response:** `404 Not Found` (if symbol is invalid)

### Example Request
`GET /api/stocks/AAPL`

### Example Response
```json
{
  "symbol": "AAPL",
  "price": 150.0,
  "timestamp": 1710753000000
}
```

---

## 2. Get Multiple Stock Prices
Returns a list of stock prices for multiple symbols provided as a comma-separated query parameter.

- **URL:** `/api/stocks`
- **Method:** `GET`
- **Query Params:** `symbols` (comma-separated string, e.g., `AAPL,MSFT,GOOG`)
- **Success Response:** `200 OK` (Invalid symbols are silently ignored)

### Example Request
`GET /api/stocks?symbols=AAPL,GOOG,UNKNOWN`

### Example Response
```json
[
  {
    "symbol": "AAPL",
    "price": 150.0,
    "timestamp": 1710753000000
  },
  {
    "symbol": "GOOG",
    "price": 2800.0,
    "timestamp": 1710753000000
  }
]
```

---

## 3. Live Stock Price Stream (SSE)
Opens a persistent connection that pushes real-time price updates every 2 seconds.

- **URL:** `/api/stocks/stream/{symbol}`
- **Method:** `GET`
- **Output Type:** `text/event-stream`
- **Behavior:** Pushes a new JSON object every 2 seconds with a price fluctuating within ±5% of the base price.

### Example Request
`curl -N http://localhost:8080/api/stocks/stream/AAPL`

### Example Output
```text
event:price-update
id:1710753002000
data:{"symbol":"AAPL","price":152.34,"timestamp":1710753002000}

event:price-update
id:1710753004000
data:{"symbol":"AAPL","price":148.56,"timestamp":1710753004000}
```

> [!TIP]
> Use the `-N` flag with `curl` to disable buffering and see events in real-time.
