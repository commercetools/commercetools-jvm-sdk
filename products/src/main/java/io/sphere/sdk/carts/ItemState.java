package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public class ItemState extends Base {
    private final long quantity;

    //TODO reference to a state + put it in creator

    @JsonCreator
    private ItemState(final long quantity) {
        this.quantity = quantity;
    }

    public static ItemState of(final long quantity) {
        return new ItemState(quantity);
    }


    public long getQuantity() {
        return quantity;
    }
}
