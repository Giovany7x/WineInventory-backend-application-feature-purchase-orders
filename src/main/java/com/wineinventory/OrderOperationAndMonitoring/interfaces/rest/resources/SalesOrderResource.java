package com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Recurso expuesto por la API para describir una orden de venta existente a los clientes.
 */
public record SalesOrderResource(
        Long id,
        String orderNumber,
        Long buyerId,
        String customerEmail,
        String status,
        LocalDateTime orderedAt,
        LocalDateTime deliveryDate,
        MoneyResource subtotalAmount,
        MoneyResource taxAmount,
        MoneyResource totalAmount,
        DeliveryInformationResource delivery,
        String notes,
        List<SalesOrderItemResource> items
) {
}
