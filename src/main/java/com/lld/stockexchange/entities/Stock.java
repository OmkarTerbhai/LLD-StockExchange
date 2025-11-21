package com.lld.stockexchange.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import lombok.*;


import java.util.*;
import java.util.UUID;

@Entity
@Table(name = "Stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock extends AbstractBaseEntity {

    @Id
    private String stockId;

    @Column
    private String symbol;

    @Column
    private String companyName;

    @OneToMany(mappedBy="stock")
    private Set<Order> orders;
}
