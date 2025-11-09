package com.lld.stockexchange.services;

import com.lld.stockexchange.dto.CreateOrderDto;
import com.lld.stockexchange.entities.Order;
import com.lld.stockexchange.entities.OrderStatus;
import com.lld.stockexchange.entities.OrderType;
import com.lld.stockexchange.entities.Stock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class OrderServiceImpl implements OrderService {

    private ConcurrentMap<String, List<Order>> orderBook = new ConcurrentHashMap<>();

    @Autowired
    private StockService stockService;

    /**
     * Method to create order
     * @param dto
     * @return
     */
    @Override
    public Optional<Order> createOrder(CreateOrderDto dto) {


        Stock st = stockService.getStockBySymbol(dto.stockSymbol());
        Order newOrder = Order.builder()
                .stock(st)
                .orderStatus(OrderStatus.ACCEPTED)
                .orderType(OrderType.getOrder(dto.orderType()))
                .quantity(dto.quantity())
                .filledQuantity(0)
                .remainingQuantity(dto.quantity())
                .price(dto.price())
                .build();

        //Find in map if stock symbol exists
        if(orderBook.containsKey(dto.stockSymbol())) {
            List<Order> orders = orderBook.get(dto.stockSymbol());

            if(Objects.isNull(orders)) {
                orders = new ArrayList<>();
            }

            orders.add(newOrder);
        }
        else {
            orderBook.put(dto.stockSymbol(), List.of(newOrder));
        }

        return Optional.of(newOrder);
    }
}
