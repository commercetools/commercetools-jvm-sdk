package io.sphere.sdk.orders;

public enum ShipmentState {

    SHIPPED("Shipped"),
    READY("Ready"),
    PENDING("Pending"),
    PARTIAL("Partial"),
    BACKORDER("Backorder");

    private final String value;

    ShipmentState(final String value) {
        this.value = value;
    }
}
