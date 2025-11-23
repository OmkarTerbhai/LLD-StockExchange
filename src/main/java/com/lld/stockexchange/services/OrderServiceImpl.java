package com.lld.stockexchange.services;

import com.lld.stockexchange.dto.CreateOrderDto;
import com.lld.stockexchange.entities.Order;
import com.lld.stockexchange.entities.OrderStatus;
import com.lld.stockexchange.entities.OrderType;
import com.lld.stockexchange.entities.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class OrderServiceImpl implements OrderService {

    private static ConcurrentMap<String, List<Order>> orderBook = new ConcurrentHashMap<>();

    private static ConcurrentMap<String, ReadWriteLock> symbolLock = new ConcurrentHashMap<>();

    /**
     * Method to create order
     * @param dto
     * @return
     */
    @Override
    public Optional<Order> createOrder(CreateOrderDto dto) {

        ReadWriteLock lock = getSymbolLock(dto.stockSymbol());

        lock.writeLock().lock();

        try {
            Order newOrder = Order.builder()
                    .orderId(UUID.randomUUID().toString())
                    .stock(dto.stockSymbol())
                    .orderStatus(OrderStatus.ACCEPTED)
                    .orderType(OrderType.getOrder(dto.orderType()))
                    .quantity(dto.quantity())
                    .filledQuantity(0)
                    .remainingQuantity(dto.quantity())
                    .price(dto.price())
                    .build();

            orderBook.computeIfAbsent(dto.stockSymbol(), (k) -> new ArrayList<>()).add(newOrder);

            return Optional.of(newOrder);
        }catch (Exception e) {
            return Optional.empty();
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Optional<Order> updateOrder(CreateOrderDto dto, String orderId) {

        ReadWriteLock lock = getSymbolLock(dto.stockSymbol());

        lock.writeLock().lock();

        try {
            ListIterator<Order> orders = orderBook.getOrDefault(dto.stockSymbol(), new ArrayList<>()).listIterator();
            Order o = null;
            while(orders.hasNext()) {
                o = orders.next();
                if(o.getOrderId().equals(orderId))
                    updateSingleOrder(dto, o);
                break;
            }

            return Optional.of(o);
        }catch (Exception e) {
            return Optional.empty();
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Optional<Order> getOrder(String orderId) {

        for(Map.Entry<String, List<Order>> mp : orderBook.entrySet()) {
            List<Order> orders = mp.getValue();

            Optional<Order> orderOp = orders.stream().filter((ele) -> ele.getOrderId().equals(orderId)).findFirst();

            return orderOp;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Order> getOrderByStockSymbol(String orderId, String stockSymbol) {
        return Optional.empty();
    }

    private void updateSingleOrder(CreateOrderDto dto, Order o) {
        o.setFilledQuantity(0);
        o.setQuantity(dto.quantity());
        o.setRemainingQuantity(dto.quantity());
        o.setOrderStatus(OrderStatus.valueOf(dto.orderStatus()));
    }

    private static ReadWriteLock getSymbolLock(String stockSymbol) {
        return symbolLock.computeIfAbsent(stockSymbol, k -> new ReentrantReadWriteLock());
    }

    public List<Order> getOrdersByStockSymbol(String symbol) {
        return orderBook.getOrDefault(symbol, List.of());
    }
}
