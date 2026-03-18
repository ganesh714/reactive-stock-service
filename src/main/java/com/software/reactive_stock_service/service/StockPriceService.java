package com.software.reactive_stock_service.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.software.reactive_stock_service.models.StockPrice;

import io.netty.util.concurrent.Ticker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StockPriceService {

	private final Map<String, Double> stockBasePrices = Map.of(
			"AAPL", 150.00,
			"GOOG", 2800.00,
			"MSFT", 300.00,
			"AMZN", 3400.00,
			"TSLA", 700.00);

	public Mono<StockPrice> getStockPrice(String symbol) {
		String upperSymbol = symbol.toUpperCase();
		Double basePrice = stockBasePrices.getOrDefault(upperSymbol, 100.00);
		return Mono.just(new StockPrice(upperSymbol, basePrice, System.currentTimeMillis()));
	}

	public Flux<StockPrice> getMultipleStockPrices(List<String> symbols) {
		List<StockPrice> fetchedPrices = new ArrayList<>();

		for (String symbol : symbols) {
			String upperSymbol = symbol.toUpperCase();
			Double basePrice = stockBasePrices.getOrDefault(upperSymbol, 100.00);
			StockPrice stockPrice = new StockPrice(upperSymbol, basePrice, System.currentTimeMillis());
			fetchedPrices.add(stockPrice);
		}

		return Flux.fromIterable(fetchedPrices);
	}

	public Flux<StockPrice> getStockPriceStream(String symbol) {
		String upperSymbol = symbol.toUpperCase();
		Double basePrice = stockBasePrices.getOrDefault(upperSymbol, 100.00);

		return Flux.interval(Duration.ofSeconds(1))
				.map(tick -> {
					double fluctuation = ThreadLocalRandom.current().nextDouble(-0.05, 0.05);
					double currentPrice = basePrice + (basePrice * fluctuation);

					// Round the price to 2 decimal places so it looks realistic
					double roundedPrice = Math.round(currentPrice * 100.0) / 100.0;

					return new StockPrice(upperSymbol, roundedPrice, System.currentTimeMillis());
				});
	}
}
