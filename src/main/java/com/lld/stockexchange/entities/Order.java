package com.lld.stockexchange.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
