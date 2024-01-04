package com.redbus.operator.repository;

import com.redbus.operator.entity.TicketCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketCostRepository extends JpaRepository<TicketCost, String> {
}
