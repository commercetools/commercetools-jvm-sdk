package io.sphere.sdk.orders;

import io.sphere.sdk.models.Base;

public class DeliveryItem extends Base {
    private final String id;
    private final long quantity;

    private DeliveryItem(final String id, final long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public static DeliveryItem of(final String id, final long quantity) {
        return new DeliveryItem(id, quantity);
    }

    public String getId() {
        return id;
    }

    public long getQuantity() {
        return quantity;
    }
}
