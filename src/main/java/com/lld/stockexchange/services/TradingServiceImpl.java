package com.lld.stockexchange.services;

import com.lld.stockexchange.dto.CreateOrderDto;
import com.lld.stockexchange.entities.Order;
import com.lld.stockexchange.entities.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TradingServiceImpl implements TradingService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMatchingStrategy matchingStrategy;

    ExecutorService executor = Executors.newFixedThreadPool(10);

    @Override
    public Order createOrder(CreateOrderDto dto) {

        Optional<Order> orderOp = orderService.createOrder(dto);

        CompletableFuture.runAsync(() -> executeNewTrade(orderOp.get()));
        return orderOp.get();
    }

    @Override
    public List<Order> getOrdersBySymbol(String symbol) {
        return orderService.getOrdersByStockSymbol(symbol);
    }

    private void executeNewTrade(Order o) {
        List<Order> orders = orderService.getOrdersByStockSymbol(o.getStock())
                .stream().filter((ele) -> !o.getOrderId().equals(ele.getOrderId())).toList();

        List<Trade> matchedTrades = matchingStrategy.getMatchingOrders(o, orders);

        //Update new current order
        orderService.updateOrder(new CreateOrderDto(
                o.getStock(),
                o.getOrderType().name(),
                o.getOrderStatus().name(),
                o.getPrice(),
                o.getQuantity()
        ),o.getOrderId());

        for(Trade trade : matchedTrades) {

        }
    }
}
