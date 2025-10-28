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
public record Money(Double amount, String currency) {

    public Money {
        if (amount == null || amount < 0) {
            throw new IllegalArgumentException("The monetary amount must be a non-negative value.");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("A valid currency code is required.");
        }
    }

    public static Money of(Double amount, String currency) {
        return new Money(amount, currency);
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", amount, currency);
    }
}
