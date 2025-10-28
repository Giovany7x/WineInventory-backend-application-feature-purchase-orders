package com.wineinventory.shared.infrastructure.api.controllers;

import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Aggregates.SalesOrder;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.Queries.GetAllSalesOrdersByBuyerIdQuery;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects.OrderStatus;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Services.SalesOrderCommandService;
import com.wineinventory.OrderOperationAndMonitoring.Domain.Services.SalesOrderQueryService;
import com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources.CreateSalesOrderResource;
import com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources.SalesOrderResource;
import com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources.UpdateSalesOrderStatusResource;
import com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.transform.SalesOrderResourceAssembler;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/v1/orders")
public class SalesOrdersController {

    private final SalesOrderCommandService salesOrderCommandService;
    private final SalesOrderQueryService salesOrderQueryService;

    public SalesOrdersController(SalesOrderCommandService salesOrderCommandService,
                                 SalesOrderQueryService salesOrderQueryService) {
        this.salesOrderCommandService = salesOrderCommandService;
        this.salesOrderQueryService = salesOrderQueryService;
    }

    @PostMapping
    public ResponseEntity<SalesOrderResource> createSalesOrder(@Valid @RequestBody CreateSalesOrderResource resource) {
        var command = SalesOrderResourceAssembler.toCommand(resource);
        SalesOrder createdOrder = salesOrderCommandService.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SalesOrderResourceAssembler.toResource(createdOrder));
    }

    @GetMapping
    public List<SalesOrderResource> getSalesOrders(@RequestParam(value = "buyerId", required = false) Long buyerId) {
        List<SalesOrder> orders;
        if (buyerId != null) {
            orders = salesOrderQueryService.handle(new GetAllSalesOrdersByBuyerIdQuery(buyerId));
        } else {
            orders = salesOrderQueryService.getAll();
        }
        return orders.stream()
                .map(SalesOrderResourceAssembler::toResource)
                .collect(Collectors.toList());
    }

    @GetMapping("/{orderId}")
    public SalesOrderResource getSalesOrderById(@PathVariable Long orderId) {
        return salesOrderQueryService.getById(orderId)
                .map(SalesOrderResourceAssembler::toResource)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Sales order with id " + orderId + " was not found"));
    }

    @PatchMapping("/{orderId}/status")
    public SalesOrderResource updateOrderStatus(@PathVariable Long orderId,
                                                @Valid @RequestBody UpdateSalesOrderStatusResource resource) {
        try {
            SalesOrder updatedOrder = salesOrderCommandService.updateStatus(orderId, resource.status());
            return SalesOrderResourceAssembler.toResource(updatedOrder);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSalesOrder(@PathVariable Long orderId) {
        try {
            salesOrderCommandService.delete(orderId);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PatchMapping("/{orderId}/complete")
    public SalesOrderResource completeSalesOrder(@PathVariable Long orderId) {
        try {
            SalesOrder updatedOrder = salesOrderCommandService.updateStatus(orderId, OrderStatus.COMPLETED);
            return SalesOrderResourceAssembler.toResource(updatedOrder);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
