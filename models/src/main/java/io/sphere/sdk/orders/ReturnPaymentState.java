package io.sphere.sdk.orders;

public enum ReturnPaymentState {
    NonRefundable("NonRefundable"),
    Initial("Initial"),
    Refunded("Refunded"),
    NotRefunded("NotRefunded");

    private final String value;

    ReturnPaymentState(final String value) {
        this.value = value;
    }
}
