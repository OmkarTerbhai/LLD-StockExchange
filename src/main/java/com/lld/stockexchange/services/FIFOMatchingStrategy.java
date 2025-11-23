package com.lld.stockexchange.services;

import com.lld.stockexchange.entities.Order;
import com.lld.stockexchange.entities.OrderStatus;
import com.lld.stockexchange.entities.OrderType;
import com.lld.stockexchange.entities.Trade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FIFOMatchingStrategy implements OrderMatchingStrategy {
    @Override
    public List<Trade> getMatchingOrders(Order o, List<Order> existingOrders) {
        if(OrderType.BUY.equals(o.getOrderType())) {
            return getMatchingSellOrders(o, existingOrders);
        }
        else {
            return getMatchingBuyOrders(o, existingOrders);
        }
    }

    private List<Trade> getMatchingSellOrders(Order o, List<Order> existingOrders) {
        List<Order> matchingOrders =  existingOrders.stream()
                .filter(or -> OrderType.SELL.equals(or.getOrderType()))
                .filter(order -> !OrderStatus.EXECUTED.equals(order.getOrderStatus()) && !OrderStatus.REJECTED.equals(order.getOrderStatus()))
                .filter(order -> order.getStock().equals(o.getStock()))
                .filter(order -> order.getPrice() <= o.getPrice())
                .sorted(Comparator.comparing(Order::getPrice).thenComparing(Order::getCreatedDate))
                .toList();

        List<Trade> trades = new ArrayList<>();

        for(Order m : matchingOrders) {
            if(o.getQuantity() <= 0) break;

            int quantity = Math.min(o.getQuantity(), m.getQuantity());


            o.setFilledQuantity(quantity);
            o.setRemainingQuantity(o.getQuantity() - quantity);
            o.setOrderStatus(OrderStatus.EXECUTED);

            m.setFilledQuantity(quantity);
            m.setRemainingQuantity(o.getQuantity() - quantity);
            if(m.getRemainingQuantity() == 0)
                m.setOrderStatus(OrderStatus.EXECUTED);
            else
                m.setOrderStatus(OrderStatus.PARTIALLY_EXECUTED);

            Trade trade = Trade.builder()
                    .buyOrder(m)
                    .sellOrder(o)
                    .price(m.getPrice())
                    .stock(o.getStock())
                    .quantity(quantity)
                    .build();

            trades.add(trade);
        }

        return trades;
    }

    private List<Trade> getMatchingBuyOrders(Order o, List<Order> existingOrders) {
        List<Order> matchingOrders = existingOrders.stream()
                .filter(or -> OrderType.BUY.equals(or.getOrderType()))
                .filter(order -> !OrderStatus.EXECUTED.equals(order.getOrderStatus()) && !OrderStatus.REJECTED.equals(order.getOrderStatus()))
                .filter(order -> order.getStock().equals(o.getStock()))
                .filter(order -> order.getPrice() >= o.getPrice())
                .sorted(Comparator.comparing(Order::getPrice).thenComparing(Order::getCreatedDate))
                .toList();

        List<Trade> trades = new ArrayList<>();

        for(Order m : matchingOrders) {
            if(o.getQuantity() <= 0) break;

            int quantity = Math.min(o.getQuantity(), m.getQuantity());


            o.setFilledQuantity(quantity);
            o.setRemainingQuantity(o.getQuantity() - quantity);

            m.setFilledQuantity(quantity);
            m.setRemainingQuantity(o.getQuantity() - quantity);

            Trade trade = Trade.builder()
                    .buyOrder(m)
                    .sellOrder(o)
                    .price(m.getPrice())
                    .stock(o.getStock())
                    .quantity(quantity)
                    .build();

            trades.add(trade);
        }
        return trades;
    }
}
