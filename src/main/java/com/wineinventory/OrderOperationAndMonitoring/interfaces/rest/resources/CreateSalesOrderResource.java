package com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateSalesOrderResource(
        @NotNull Long buyerId,
        @NotBlank String currency,
        @NotEmpty @Valid List<CreateSalesOrderItemResource> items,
        @NotNull @Valid DeliveryInformationResource delivery,
        String notes
) {
}
