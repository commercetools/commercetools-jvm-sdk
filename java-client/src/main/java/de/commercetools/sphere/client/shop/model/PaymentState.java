package io.sphere.client.shop.model;

/** * Describes the payment state of an {@link Order}. */
public enum PaymentState {
    BalanceDue, Failed, Pending, CreditOwed, Paid
}
