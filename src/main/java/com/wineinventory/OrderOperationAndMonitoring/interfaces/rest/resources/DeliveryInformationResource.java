package com.wineinventory.OrderOperationAndMonitoring.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

/**
 * Payload con los datos necesarios para entregar una orden al cliente final.
 */
public record DeliveryInformationResource(
        @NotBlank String recipientName,
        @NotBlank String contactPhone,
        @NotBlank String addressLine,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String postalCode,
        @NotBlank String country
) {
}
