package com.pding.usermanagement.supply_house.models.request;

import com.pding.usermanagement.supply_house.models.enums.RelationshipStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RespondToInvitationRequest {

    @NotNull(message = "Status cannot be null")
    private RelationshipStatus status;

}
