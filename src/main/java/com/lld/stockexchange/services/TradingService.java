package com.lld.stockexchange.services;
import com.lld.stockexchange.dto.CreateOrderDto;

public interface TradingService {

	boolean createOrder(CreateOrderDto dto);
}
