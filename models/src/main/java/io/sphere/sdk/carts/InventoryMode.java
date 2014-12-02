package io.sphere.sdk.carts;

public enum InventoryMode {
    TrackOnly, ReserveOnOrder, None;

    public static InventoryMode defaultValue() {
        return InventoryMode.None;
    }
}
