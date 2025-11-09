package com.lld.stockexchange.dto;

public record CreateOrderDto(
        String stockSymbol,
        String orderType,
        double price
) {
}
