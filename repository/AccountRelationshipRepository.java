package com.pding.usermanagement.supply_house.repository;

import com.pding.usermanagement.supply_house.models.MAccountRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRelationshipRepository extends JpaRepository<MAccountRelationship, String> {

    List<MAccountRelationship> findByBusinessOwnerId(String businessOwnerId);

    Optional<MAccountRelationship> findByBusinessOwnerIdAndSubAccountId(String businessOwnerId, String subAccountId);

    List<MAccountRelationship> findBySubAccountId(String subAccountId);

}
