package com.lld.stockexchange.services;

import com.lld.stockexchange.entities.Order;

import java.util.List;

public interface OrderMatchingStrategy {

    List<Order> getMatchingOrders(Order o, List<Order> existingOrders);
}
