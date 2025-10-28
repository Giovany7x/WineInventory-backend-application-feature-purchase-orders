package com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources;

import java.time.LocalDateTime;
import java.util.List;

public record SalesOrderResource(
        Long id,
        String orderNumber,
        Long buyerId,
        String status,
        LocalDateTime orderedAt,
        MoneyResource totalAmount,
        DeliveryInformationResource delivery,
        String notes,
        List<SalesOrderItemResource> items
) {
}
