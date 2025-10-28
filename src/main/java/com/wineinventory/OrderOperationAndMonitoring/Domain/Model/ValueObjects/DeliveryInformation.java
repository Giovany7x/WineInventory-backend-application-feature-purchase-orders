package com.wineinventory.OrderOperationAndMonitoring.Domain.Model.ValueObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * Value object que encapsula los detalles de entrega asociados a una orden de venta.
 * Se define como embebible para que pueda reutilizarse en diferentes entidades sin crear
 * una tabla independiente; cada campo se persiste junto con la orden.
 */
@Embeddable
public class DeliveryInformation implements Deliverable {

    /** Nombre de la persona que recibirá el pedido. */
    @Column(name = "delivery_recipient_name", nullable = false)
    private String recipientName;

    /** Número telefónico de contacto del destinatario. */
    @Column(name = "delivery_contact_phone", nullable = false)
    private String contactPhone;

    /** Dirección donde se realizará la entrega. */
    @Column(name = "delivery_address_line", nullable = false)
    private String addressLine;

    /** Ciudad del destino de entrega. */
    @Column(name = "delivery_city", nullable = false)
    private String city;

    /** Estado o provincia del destino de entrega. */
    @Column(name = "delivery_state", nullable = false)
    private String state;

    /** Código postal asociado a la dirección de entrega. */
    @Column(name = "delivery_postal_code", nullable = false)
    private String postalCode;

    /** País donde se encuentra el destinatario. */
    @Column(name = "delivery_country", nullable = false)
    private String country;

    protected DeliveryInformation() {
        // Required by JPA
    }

    /**
     * Crea la información de entrega exigiendo todos los datos necesarios para coordinar el envío.
     */
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
