package com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.transform;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates.SalesOrder;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Commands.GenerateSalesOrderCommand;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Entities.SalesOrderItem;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects.DeliveryInformation;
import com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources.*;
import com.wineinventory.shared.domain.model.valueobjects.Money;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase utilitaria que traduce entre los recursos expuestos por la API y los modelos del dominio.
 */
public final class SalesOrderResourceAssembler {

    private SalesOrderResourceAssembler() {
    }

    /**
     * Convierte un recurso de creación recibido por la API en el comando de aplicación correspondiente.
     */
    public static GenerateSalesOrderCommand toCommand(CreateSalesOrderResource resource) {
        DeliveryInformation deliveryInformation = new DeliveryInformation(
                resource.delivery().recipientName(),
                resource.delivery().contactPhone(),
                resource.delivery().addressLine(),
                resource.delivery().city(),
                resource.delivery().state(),
                resource.delivery().postalCode(),
                resource.delivery().country());
        List<GenerateSalesOrderCommand.Item> items = resource.items().stream()
                .map(item -> new GenerateSalesOrderCommand.Item(
                        item.productId(),
                        item.productName(),
                        item.quantity(),
                        item.unitPrice()))
                .collect(Collectors.toList());
        return new GenerateSalesOrderCommand(
                resource.buyerId(),
                resource.currency(),
                items,
                deliveryInformation,
                resource.notes());
    }

    /**
     * Transforma un agregado de orden en el recurso listo para serializarse y enviarse al cliente.
     */
    public static SalesOrderResource toResource(SalesOrder order) {
        List<SalesOrderItemResource> orderItems = order.getItems().stream()
                .map(SalesOrderResourceAssembler::toResource)
                .collect(Collectors.toList());
        Money total = order.getTotalAmount();
        DeliveryInformation delivery = order.getDeliveryInformation();
        return new SalesOrderResource(
                order.getId(),
                order.getOrderNumber(),
                order.getBuyerId(),
                order.getStatus().name(),
                order.getOrderedAt(),
                new MoneyResource(total.amount(), total.currency()),
                new DeliveryInformationResource(
                        delivery.getRecipientName(),
                        delivery.getContactPhone(),
                        delivery.getAddressLine(),
                        delivery.getCity(),
                        delivery.getState(),
                        delivery.getPostalCode(),
                        delivery.getCountry()),
                order.getNotes(),
                orderItems);
    }

    /**
     * Construye el recurso de un ítem individual a partir de la entidad del dominio.
     */
    private static SalesOrderItemResource toResource(SalesOrderItem item) {
        Money unitPrice = item.getUnitPrice();
        Money lineTotal = item.getLineTotal();
        return new SalesOrderItemResource(
                item.getId(),
                item.getProductId(),
                item.getProductName(),
                item.getQuantity(),
                new MoneyResource(unitPrice.amount(), unitPrice.currency()),
                new MoneyResource(lineTotal.amount(), lineTotal.currency()));
    }
}
