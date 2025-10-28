package com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Commands;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects.DeliveryInformation;
import java.util.List;

/**
 * Comando de aplicación que encapsula toda la información necesaria para crear
 * una nueva orden de venta desde la capa de interfaces.
 */
public record GenerateSalesOrderCommand(
        Long buyerId,
        String currency,
        List<Item> items,
        DeliveryInformation deliveryInformation,
        String notes
) {

    /**
     * Estructura ligera que describe cada ítem que se desea incluir en la orden.
     */
    public record Item(Long productId, String productName, Integer quantity, Double unitPrice) {
    }
}
