package com.pding.usermanagement.supply_house.service;

import com.pding.usermanagement.supply_house.exceptions.AccountNotFoundException;
import com.pding.usermanagement.supply_house.exceptions.RelationshipNotFoundException;
import com.pding.usermanagement.supply_house.models.MAccount;
import com.pding.usermanagement.supply_house.models.MAccountRelationship;
import com.pding.usermanagement.supply_house.models.enums.RelationshipStatus;
import com.pding.usermanagement.supply_house.models.response.ApiResponse;
import com.pding.usermanagement.supply_house.repository.AccountRelationshipRepository;
import com.pding.usermanagement.supply_house.repository.AccountRepository;
import com.pding.usermanagement.supply_house.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRelationshipRepository accountRelationshipRepository;

    /**
     * Upgrades an account to a business owner if it has placed at least 10 orders in the last year.
     *
     * @param accountId The ID of the account to upgrade.
     * @return A success message if the account is upgraded, otherwise an error message.
     * @throws AccountNotFoundException if the account is not found.
     * @throws IllegalArgumentException if the account does not meet the upgrade criteria.
     */
    public ResponseEntity<ApiResponse<?>> upgradeToBusinessOwner(String accountId) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        Date oneYearAgo = calendar.getTime();

        long orderCount = accountRepository.countOrdersInLastYear(accountId, oneYearAgo);

        if (orderCount >= 10) {
            MAccount account = accountRepository
                    .findById(accountId)
                    .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + accountId));

            account.setBusinessOwner(true);

            accountRepository.save(account);

            return ResponseEntity.ok(ApiResponse.successResponse("Account upgraded successfully!"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.failedResponse("Failed to upgrade account to business owner."));
        }
    }

    /**
     * Sends an invitation from a business owner account to a subAccount.
     *
     * @param businessOwnerId The ID of the business owner account.
     * @param subAccountId    The ID of the subAccount.
     * @return A success message if the invitation is sent.
     * @throws AccountNotFoundException if either the business owner account or subAccount is not found.
     */
    public ResponseEntity<ApiResponse<?>> sendInvitation(String businessOwnerId, String subAccountId) {
        MAccountRelationship relationship = MAccountRelationship.createAccountRelation(
                businessOwnerId,
                subAccountId,
                RelationshipStatus.PENDING
        );

        accountRelationshipRepository.save(relationship);

        return ResponseEntity.ok(ApiResponse.successResponse("Invitation sent successfully."));
    }

    /**
     * Responds to an invitation to join a business owner account.
     *
     * @param accountId      The ID of the account responding to the invitation.
     * @param relationshipId The ID of the relationship.
     * @param status         The status of the response (ACCEPTED or DECLINED).
     * @return A success message if the response is processed.
     * @throws AccountNotFoundException if the relationship / invitation is not found.
     * @throws IllegalArgumentException if the account is not authorized to respond to the invitation.
     */
    public ResponseEntity<ApiResponse<?>> respondToInvitation(String accountId, String relationshipId, RelationshipStatus status) {
        MAccountRelationship relationship = accountRelationshipRepository.findById(relationshipId)
                .orElseThrow(() -> new RelationshipNotFoundException("Invitation does not exist."));

        if (!relationship.getSubAccountId().equals(accountId)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.failedResponse("You are not authorized to respond to this invitation."));
        }

        relationship.setRelationshipStatus(status);
        relationship.setLastUpdated(new Date());

        accountRelationshipRepository.save(relationship);

        return ResponseEntity.ok(ApiResponse.successResponse("Invitation status updated successfully."));
    }

    /**
     * Unlinks a subaccount from a business owner account.
     *
     * @param businessOwnerId The ID of the business owner account.
     * @param subAccountId    The ID of the subAccount to unlink.
     * @return A success message if the subAccount is unlinked.
     * @throws AccountNotFoundException if the business owner account or subAccount relationship is not found.
     */
    public ResponseEntity<ApiResponse<?>> unlinkSubAccount(String businessOwnerId, String subAccountId) {

        MAccountRelationship relationship = accountRelationshipRepository
                .findByBusinessOwnerIdAndSubAccountId(businessOwnerId, subAccountId)
                .orElseThrow(() -> new RelationshipNotFoundException("Invitation does not exist."));

        accountRelationshipRepository.delete(relationship);

        return ResponseEntity.ok(ApiResponse.successResponse("SubAccount unlinked successfully."));
    }

}
