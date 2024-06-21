package com.pding.usermanagement.supply_house.models;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Getter
@Entity
@Table(name = "order")
public class MOrder {

    @Id
    @UuidGenerator
    private String orderId;

    private String accountId;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

}
