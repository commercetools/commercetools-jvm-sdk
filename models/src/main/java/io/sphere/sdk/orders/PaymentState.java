package io.sphere.sdk.orders;

public enum PaymentState {
    BalanceDue("BalanceDue"),
    Failed("Failed"),
    Pending("Pending"),
    CreditOwed("CreditOwed"),
    Paid("Paid");

    private final String value;

    PaymentState(final String value) {
        this.value = value;
    }
}
