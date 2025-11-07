package com.lld.stockexchange.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class AbstractBaseEntity {

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastEditedDate;

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column
    private String comments;

    @Column
    private String lastEditedBy;

    @Column
    private String lastCreatedBy;

    @PrePersist
    protected void prePersist() {
        this.lastEditedDate = new Date();
        this.createdDate = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.lastEditedDate = new Date();
        this.createdDate = new Date();
    }
}
