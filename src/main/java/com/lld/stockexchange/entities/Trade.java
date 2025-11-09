package com.lld.stockexchange.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trade extends AbstractBaseEntity {

    @Id
    private String tradeId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "sellOrderId")
    private Order buyOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "sellOrderId")
    private Order sellOrder;

    @Column
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "stockId")
    private Stock stock;

    @Column
    private int quantity;
}
