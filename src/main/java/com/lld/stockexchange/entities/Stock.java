package com.lld.stockexchange.entities;


import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Stock")
public class Stock extends AbstractBaseEntity {

    @Id
    private String stockId;

    @Column
    private String symbol;

    @Column
    private String companyName;

    @OneToMany
    private Set<Order> orders;
}
