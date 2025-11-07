package com.lld.stockexchange.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
