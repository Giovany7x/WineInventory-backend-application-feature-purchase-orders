package com.wineinventory.OrderOperationAndMonitoring.Application.internal.commandservices;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates.SalesOrder;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Commands.GenerateSalesOrderCommand;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Entities.SalesOrderItem;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects.OrderStatus;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Repositories.SalesOrderRepository;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Services.SalesOrderCommandService;
import com.wineinventory.shared.domain.model.valueobjects.Money;
import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SalesOrderCommandServiceImpl implements SalesOrderCommandService {

    private final SalesOrderRepository salesOrderRepository;

    public SalesOrderCommandServiceImpl(SalesOrderRepository salesOrderRepository) {
        this.salesOrderRepository = salesOrderRepository;
    }

    @Override
    public SalesOrder handle(GenerateSalesOrderCommand command) {
        Objects.requireNonNull(command, "The command to generate a sales order is required");
        if (command.items() == null || command.items().isEmpty()) {
            throw new IllegalArgumentException("A sales order requires at least one item");
        }
        if (command.currency() == null || command.currency().isBlank()) {
            throw new IllegalArgumentException("A valid currency code is required for the sales order");
        }
        SalesOrder salesOrder = SalesOrder.create(command.buyerId(), command.currency(), command.deliveryInformation(), command.notes());
        command.items().forEach(item -> {
            validateItem(item);
            Money unitPrice = Money.of(item.unitPrice(), command.currency());
            SalesOrderItem salesOrderItem = new SalesOrderItem(item.productId(), item.productName(), item.quantity(), unitPrice);
            salesOrder.addItem(salesOrderItem);
        });
        return salesOrderRepository.save(salesOrder);
    }

    @Override
    public SalesOrder updateStatus(Long orderId, OrderStatus newStatus) {
        SalesOrder salesOrder = salesOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Sales order with id " + orderId + " was not found"));
        salesOrder.updateStatus(newStatus);
        return salesOrderRepository.save(salesOrder);
    }

    @Override
    public void delete(Long orderId) {
        if (!salesOrderRepository.existsById(orderId)) {
            throw new EntityNotFoundException("Sales order with id " + orderId + " was not found");
        }
        salesOrderRepository.deleteById(orderId);
    }

    private void validateItem(GenerateSalesOrderCommand.Item item) {
        Objects.requireNonNull(item.productId(), "The item product identifier is required");
        Objects.requireNonNull(item.productName(), "The item product name is required");
        Objects.requireNonNull(item.quantity(), "The item quantity is required");
        Objects.requireNonNull(item.unitPrice(), "The item unit price is required");
        if (item.quantity() <= 0) {
            throw new IllegalArgumentException("The item quantity must be greater than zero");
        }
        if (item.unitPrice() <= 0) {
            throw new IllegalArgumentException("The item unit price must be greater than zero");
        }
    }
}
