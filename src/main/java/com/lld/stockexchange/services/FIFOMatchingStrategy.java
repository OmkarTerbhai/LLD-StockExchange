package com.lld.stockexchange.services;

import com.lld.stockexchange.entities.Order;
import com.lld.stockexchange.entities.OrderStatus;
import com.lld.stockexchange.entities.OrderType;

import java.util.Comparator;
import java.util.List;

public class FIFOMatchingStrategy implements OrderMatchingStrategy {
    @Override
    public List<Order> getMatchingOrders(Order o, List<Order> existingOrders) {
        if(OrderType.BUY.equals(o.getOrderType())) {
            return getMatchingSellOrders(o, existingOrders);
        }
        else {
            return getMatchingBuyOrders(o, existingOrders);
        }
    }

    private List<Order> getMatchingSellOrders(Order o, List<Order> existingOrders) {
        return existingOrders.stream()
                .filter(or -> OrderType.SELL.equals(or.getOrderType()))
                .filter(order -> !OrderStatus.EXECUTED.equals(order.getOrderStatus()) && !OrderStatus.REJECTED.equals(order.getOrderStatus()))
                .filter(order -> order.getStock().getStockId().equals(o.getStock().getStockId()))
                .filter(order -> order.getPrice() <= o.getPrice())
                .sorted(Comparator.comparing(Order::getPrice).thenComparing(Order::getCreatedDate))
                .toList();
    }

    private List<Order> getMatchingBuyOrders(Order o, List<Order> existingOrders) {
        return existingOrders.stream()
                .filter(or -> OrderType.BUY.equals(or.getOrderType()))
                .filter(order -> !OrderStatus.EXECUTED.equals(order.getOrderStatus()) && !OrderStatus.REJECTED.equals(order.getOrderStatus()))
                .filter(order -> order.getStock().getStockId().equals(o.getStock().getStockId()))
                .filter(order -> order.getPrice() >= o.getPrice())
                .sorted(Comparator.comparing(Order::getPrice).thenComparing(Order::getCreatedDate))
                .toList();
    }
}
