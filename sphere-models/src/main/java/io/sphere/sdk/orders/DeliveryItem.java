package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.carts.LineItemLike;
import io.sphere.sdk.models.Base;

public class DeliveryItem extends Base {
    private final String id;
    private final Long quantity;

    @JsonCreator
    private DeliveryItem(final String id, final Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public static DeliveryItem of(final String id, final long quantity) {
        return of(id, Long.valueOf(quantity));
    }

    public static DeliveryItem of(final String id, final Long quantity) {
        return new DeliveryItem(id, quantity);
    }

    @JsonIgnore
    public static DeliveryItem of(final LineItemLike lineItem, final Long quantity) {
        return of(lineItem.getId(), quantity);
    }

    @JsonIgnore
    public static DeliveryItem of(final LineItemLike lineItem) {
        return of(lineItem, lineItem.getQuantity());
    }
}
