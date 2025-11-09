package com.lld.stockexchange.services;

import com.lld.stockexchange.dto.CreateOrderDto;
import com.lld.stockexchange.entities.Order;

import java.util.Optional;

public interface OrderService {

    Optional<Order> createOrder(CreateOrderDto  dto);
}
