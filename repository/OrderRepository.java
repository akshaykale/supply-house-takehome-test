package com.pding.usermanagement.supply_house.repository;

import com.pding.usermanagement.supply_house.models.MOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<MOrder, String> {
}
