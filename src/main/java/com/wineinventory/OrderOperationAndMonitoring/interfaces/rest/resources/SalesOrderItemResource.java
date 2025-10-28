package com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources;

/**
 * Representa un ítem individual cuando una orden se devuelve al cliente vía API.
 */
public record SalesOrderItemResource(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        MoneyResource unitPrice,
        MoneyResource lineTotal
) {
}
