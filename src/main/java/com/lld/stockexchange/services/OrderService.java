package com.lld.stockexchange.services;

import com.lld.stockexchange.dto.CreateOrderDto;
import com.lld.stockexchange.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<Order> createOrder(CreateOrderDto  dto);

    Optional<Order> updateOrder(CreateOrderDto dto, String orderId);

    Optional<Order> getOrder(String orderId);

    Optional<Order> getOrderByStockSymbol(String orderId, String stockSymbol);

    List<Order> getOrdersByStockSymbol(String symbol);
}
