package com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects;

/**
 * Contrato para value objects que describen la información de entrega de una orden.
 */
public interface Deliverable {
    String getRecipientName();

    String getContactPhone();

    String getAddressLine();

    String getCity();

    String getState();

    String getPostalCode();

    String getCountry();
}
