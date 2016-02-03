package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

final class DeliveryItemImpl extends Base implements DeliveryItem {
    private final String id;
    private final Long quantity;

    @JsonCreator
    DeliveryItemImpl(final String id, final Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Long getQuantity() {
        return quantity;
    }
}
