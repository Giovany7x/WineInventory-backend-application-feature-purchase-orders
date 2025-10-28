package com.wineinventory.OrderOperationAndMonitoring.Domain.Services;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates.SalesOrder;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Queries.GetAllSalesOrdersByBuyerIdQuery;
import java.util.List;
import java.util.Optional;

public interface SalesOrderQueryService {
    List<SalesOrder> handle(GetAllSalesOrdersByBuyerIdQuery query);

    Optional<SalesOrder> getById(Long orderId);

    List<SalesOrder> getAll();
}
