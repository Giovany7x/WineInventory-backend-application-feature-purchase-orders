package com.wineinventory.shared.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Represents a monetary value with an amount and a currency.
 * This class is immutable and can be used as a value object in domain models.
 *
 * @summary
 * The Money class encapsulates a monetary amount and its associated currency.
 * It is designed to be used in financial calculations and transactions.
 *
 * @since 1.0.0
 */
@Embeddable
public record Money(Double amount, String Currency) {

    @Override
    public String toString() {
        return String.format("%.2f %s", amount, Currency);
    }
}
