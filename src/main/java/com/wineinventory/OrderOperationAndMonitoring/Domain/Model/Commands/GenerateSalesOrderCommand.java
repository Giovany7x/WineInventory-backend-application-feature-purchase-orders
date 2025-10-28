package com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Commands;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects.DeliveryInformation;
import java.util.List;

/**
 * Command used to generate a new sales order aggregate.
 */
public record GenerateSalesOrderCommand(
        Long buyerId,
        String currency,
        List<Item> items,
        DeliveryInformation deliveryInformation,
        String notes
) {

    public record Item(Long productId, String productName, Integer quantity, Double unitPrice) {
    }
}
