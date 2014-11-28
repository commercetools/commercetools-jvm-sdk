package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.carts.LineItemLike;
import io.sphere.sdk.models.Base;

public class DeliveryItem extends Base {
    private final String id;
    private final long quantity;

    private DeliveryItem(final String id, final long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public long getQuantity() {
        return quantity;
    }

    public static DeliveryItem of(final String id, final long quantity) {
        return new DeliveryItem(id, quantity);
    }

    @JsonIgnore
    public static DeliveryItem of(final LineItemLike lineItem, final long quantity) {
        return of(lineItem.getId(), quantity);
    }

    @JsonIgnore
    public static DeliveryItem of(final LineItemLike lineItem) {
        return of(lineItem, lineItem.getQuantity());
    }
}
