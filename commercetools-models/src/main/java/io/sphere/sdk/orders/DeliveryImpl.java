package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import java.time.ZonedDateTime;
import java.util.List;

final class DeliveryImpl extends Base implements Delivery {
    private final String id;
    private final ZonedDateTime createdAt;
    private final List<DeliveryItem> items;
    private final List<Parcel> parcels;

    @JsonCreator
    DeliveryImpl(final String id, final ZonedDateTime createdAt, final List<DeliveryItem> items, final List<Parcel> parcels) {
        this.id = id;
        this.createdAt = createdAt;
        this.items = items;
        this.parcels = parcels;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public List<DeliveryItem> getItems() {
        return items;
    }

    @Override
    public List<Parcel> getParcels() {
        return parcels;
    }
}
