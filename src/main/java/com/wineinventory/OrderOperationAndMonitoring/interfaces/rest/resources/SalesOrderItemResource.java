package com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources;

public record SalesOrderItemResource(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        MoneyResource unitPrice,
        MoneyResource lineTotal
) {
}
