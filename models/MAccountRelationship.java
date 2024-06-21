package com.pding.usermanagement.supply_house.models;

import com.pding.usermanagement.supply_house.models.enums.RelationshipStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "account_relationship")
public class MAccountRelationship {

    @Id
    @UuidGenerator
    private String relationshipId;

    private String businessOwnerId;

    private String subAccountId;

    @Enumerated(EnumType.STRING)
    private RelationshipStatus relationshipStatus;

    @Temporal(TemporalType.DATE)
    private Date lastUpdated;

    public static MAccountRelationship createAccountRelation(String businessOwnerId, String subAccountId, RelationshipStatus status) {
        MAccountRelationship relationship = new MAccountRelationship();
        relationship.setBusinessOwnerId(businessOwnerId);
        relationship.setSubAccountId(subAccountId);
        relationship.setRelationshipStatus(status);
        relationship.setLastUpdated(new Date());
        return relationship;
    }

}
