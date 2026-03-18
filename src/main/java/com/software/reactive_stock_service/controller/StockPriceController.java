package com.software.reactive_stock_service.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software.reactive_stock_service.models.StockPrice;
import com.software.reactive_stock_service.service.StockPriceService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class StockPriceController {

	@Autowired
	StockPriceService stockPriceService;
	
	@GetMapping("/api/stocks/{symbol}")
	public Mono<StockPrice> getStockPrice(@PathVariable String symbol){
		return stockPriceService.getStockPrice(symbol);
	}
	
	@GetMapping("/api/stocks")
	public Flux<StockPrice> getMultipleStockPrices(@RequestParam String symbols) {
		List<String> symbolsList = Arrays.asList(symbols.split(","));
		return stockPriceService.getMultipleStockPrices(symbolsList);
	}
	
	@GetMapping( value =  "/api/stocks/stream/{symbol}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ServerSentEvent<StockPrice>> getStockPriceStream(@PathVariable String symbol) {
		return stockPriceService.getStockPriceStream(symbol)
                .map(stockPrice -> ServerSentEvent.<StockPrice>builder()
                        .id(String.valueOf(stockPrice.timestamp())) // Optional: giving each event an ID
                        .event("price-update")                      // Optional: naming the event type
                        .data(stockPrice)                           // The actual JSON payload
                        .build());
	}


}
