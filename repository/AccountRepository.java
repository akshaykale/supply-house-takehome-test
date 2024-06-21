package com.pding.usermanagement.supply_house.repository;

import com.pding.usermanagement.supply_house.models.MAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AccountRepository extends JpaRepository<MAccount, String> {

    @Query("SELECT COUNT(o) FROM Order o WHERE o.accountId = :accountId AND o.orderDate >= :oneYearAgo")
    long countOrdersInLastYear(@Param("accountId") String accountId, @Param("oneYearAgo") Date oneYearAgo);

}
