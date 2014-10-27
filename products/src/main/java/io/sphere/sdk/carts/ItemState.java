package io.sphere.sdk.carts;

import io.sphere.sdk.models.Base;

public class ItemState extends Base {
    private final long quantity;

    //TODO reference to a state

    private ItemState(final long quantity) {
        this.quantity = quantity;
    }

    //TODO public creator


    public long getQuantity() {
        return quantity;
    }
}
