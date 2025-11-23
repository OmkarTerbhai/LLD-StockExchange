package com.lld.stockexchange.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name= "Orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends AbstractBaseEntity {

    @Id
    private String orderId;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "stockId")
    private String stock;

    @Column(name = "price")
    private double price;

    @Column
    private int quantity;

    @Column
    private int filledQuantity;

    @Column
    private int remainingQuantity;

    @Column
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column
    @Enumerated(value = EnumType.STRING)
    private OrderType orderType;

    @OneToMany(mappedBy="buyOrder")
    private Set<Trade> buyTrades;

    @OneToMany(mappedBy="sellOrder")
    private Set<Trade> sellTrades;
}
