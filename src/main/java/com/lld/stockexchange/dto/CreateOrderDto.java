package com.lld.stockexchange.dto;

public record CreateOrderDto(
        String stockSymbol,
        String orderType,
        String orderStatus,
        double price,
        int quantity
) {
}
