package com.lld.stockexchange.services;
import com.lld.stockexchange.dto.CreateOrderDto;
import com.lld.stockexchange.entities.Order;

import java.util.List;

public interface TradingService {

	Order createOrder(CreateOrderDto dto);

    List<Order> getOrdersBySymbol(String symbol);
}
