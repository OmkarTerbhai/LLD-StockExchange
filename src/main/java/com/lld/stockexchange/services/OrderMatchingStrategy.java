package com.lld.stockexchange.services;

import com.lld.stockexchange.entities.Order;
import com.lld.stockexchange.entities.Trade;

import java.util.List;

public interface OrderMatchingStrategy {

    List<Trade> getMatchingOrders(Order o, List<Order> existingOrders);
}
