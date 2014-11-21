package io.sphere.sdk.orders;

public enum OrderState {
    OPEN("Open"),
    COMPLETE("Complete"),
    CANCELLED("Cancelled");

    private final String value;

    OrderState(final String value) {
        this.value = value;
    }
}
