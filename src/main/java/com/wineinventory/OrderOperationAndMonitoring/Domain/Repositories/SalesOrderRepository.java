package com.wineinventory.OrderOperationAndMonitoring.Domain.Repositories;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates.SalesOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    List<SalesOrder> findAllByBuyerId(Long buyerId);

    Optional<SalesOrder> findByOrderNumber(String orderNumber);
}
