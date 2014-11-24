package io.sphere.sdk.carts;

public enum InventoryMode {
    TrackOnly("TrackOnly"),
    ReserveOnOrder("ReserveOnOrder"),
    None("None");

    private final String value;

    InventoryMode(final String value) {
        this.value = value;
    }

    public static InventoryMode defaultValue() {
        return InventoryMode.None;
    }
}
