package com.pding.usermanagement.supply_house.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Getter
@Entity
@Table(name = "account")
public class MAccount {

    @Id
    @UuidGenerator
    private String accountId;

    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Setter
    private boolean isBusinessOwner;

}
