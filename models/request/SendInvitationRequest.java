package com.pding.usermanagement.supply_house.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendInvitationRequest {

    @NotNull(message = "subAccount cannot be null")
    private String subAccountId;

}
