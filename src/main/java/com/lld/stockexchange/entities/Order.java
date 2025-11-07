package com.lld.stockexchange.entities;

import jakarta.persistence.*;

@Entity
@Table
public class Order extends AbstractBaseEntity {

    @Id
    private String orderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "stockId")
    private Stock stock;

    @Column(name = "price")
    private double price;

    @Column
    private int quantity;

    @Column
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column
    @Enumerated(value = EnumType.STRING)
    private OrderType orderType;
}
