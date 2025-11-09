package com.lld.stockexchange.entities;

public enum OrderType {
    BUY("Buy"), SELL("Sell");


    private String name;
    OrderType(String name) {
        this.name = name;
    }

    public static OrderType getOrder(String name) {
        for(OrderType type : OrderType.values()) {
            if(type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }
}
