package io.sphere.sdk.orders;

public enum ReturnShipmentState {

    ADVISED("Advised"),
    RETURNED("Returned"),
    BACKINSTOCK("BackInStock"),
    UNUSABLE("Unusable");

    private final String value;

    ReturnShipmentState(final String value) {
        this.value = value;
    }
}
