package com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * Value object that represents the delivery details associated with a sales order.
 */
@Embeddable
public class DeliveryInformation implements Deliverable {

    @Column(name = "delivery_recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "delivery_contact_phone", nullable = false)
    private String contactPhone;

    @Column(name = "delivery_address_line", nullable = false)
    private String addressLine;

    @Column(name = "delivery_city", nullable = false)
    private String city;

    @Column(name = "delivery_state", nullable = false)
    private String state;

    @Column(name = "delivery_postal_code", nullable = false)
    private String postalCode;

    @Column(name = "delivery_country", nullable = false)
    private String country;

    protected DeliveryInformation() {
        // Required by JPA
    }

    public DeliveryInformation(String recipientName, String contactPhone, String addressLine,
                               String city, String state, String postalCode, String country) {
        this.recipientName = Objects.requireNonNull(recipientName, "Recipient name is required");
        this.contactPhone = Objects.requireNonNull(contactPhone, "Contact phone is required");
        this.addressLine = Objects.requireNonNull(addressLine, "Address line is required");
        this.city = Objects.requireNonNull(city, "City is required");
        this.state = Objects.requireNonNull(state, "State is required");
        this.postalCode = Objects.requireNonNull(postalCode, "Postal code is required");
        this.country = Objects.requireNonNull(country, "Country is required");
    }

    @Override
    public String getRecipientName() {
        return recipientName;
    }

    @Override
    public String getContactPhone() {
        return contactPhone;
    }

    @Override
    public String getAddressLine() {
        return addressLine;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String getCountry() {
        return country;
    }
}
