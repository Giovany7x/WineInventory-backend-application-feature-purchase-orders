package com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects;

/**
 * Contract for value objects that describe delivery information for an order.
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
