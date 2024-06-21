package com.pding.usermanagement.supply_house.controller;

import com.pding.usermanagement.supply_house.models.request.RespondToInvitationRequest;
import com.pding.usermanagement.supply_house.models.request.SendInvitationRequest;
import com.pding.usermanagement.supply_house.models.response.ApiResponse;
import com.pding.usermanagement.supply_house.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired
    private AccountService accountService;

    /**
     * Use to upgrade to the business owner account
     *
     * @param accountId requesting user id
     * @return ApiResponse with the result
     */
    @PostMapping("/{accountId}/upgrade")
    public ResponseEntity<ApiResponse<?>> upgradeToBusinessOwner(@PathVariable String accountId) {
        return accountService.upgradeToBusinessOwner(accountId);
    }

    /**
     * Send invitation to a subAccount
     *
     * @param businessOwnerId - who is inviting | we can verify this ID from a JWT token as well
     * @param request         - contains the subAccount
     * @return ApiResponse with the result
     */
    @PostMapping("/{businessOwnerId}/invite")
    public ResponseEntity<ApiResponse<?>> sendInvitation(
            @PathVariable String businessOwnerId,
            @Valid @RequestBody SendInvitationRequest request
    ) {
        return accountService.sendInvitation(businessOwnerId, request.getSubAccountId());
    }

    /**
     * Respond to Invitation
     *
     * @param accountId      - user who will accept or decline the invitation
     * @param relationshipId - invitation ID we can say
     * @param request        - contains the status ACCEPTED | REJECTED
     * @return ApiResponse with the result
     */
    @PatchMapping("/{accountId}/invitations/{relationshipId}")
    public ResponseEntity<ApiResponse<?>> respondToInvitation(
            @PathVariable String accountId,
            @PathVariable String relationshipId,
            @Valid @RequestBody RespondToInvitationRequest request
    ) {
        return accountService.respondToInvitation(accountId, relationshipId, request.getStatus());
    }

    /**
     * Unlink the account.
     *
     * @param businessOwnerId - the business ID
     * @param subAccountId    - the subAccount ID
     * @return ApiResponse with the result
     */
    @DeleteMapping("/{businessOwnerId}/subAccounts/{subAccountId}")
    public ResponseEntity<ApiResponse<?>> unlinkSubAccount(@PathVariable String businessOwnerId, @PathVariable String subAccountId) {
        return accountService.unlinkSubAccount(businessOwnerId, subAccountId);
    }

}
