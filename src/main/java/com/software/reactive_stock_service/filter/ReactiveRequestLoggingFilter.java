package com.software.reactive_stock_service.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class ReactiveRequestLoggingFilter implements WebFilter{
	
	private static final Logger log = LoggerFactory.getLogger(ReactiveRequestLoggingFilter.class);
	
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		long startTime = System.currentTimeMillis();
		
		ServerHttpRequest request = exchange.getRequest();
		
		return chain.filter(exchange)
				.doFinally(SignalType -> {
					long duration = System.currentTimeMillis() - startTime;
					log.info("Request: {} {}, Duration: {}ms", request.getMethod(), request.getPath(), duration);
				});
	}
}
