package io.sphere.sdk.orders;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.CreationTimestamped;
import io.sphere.sdk.models.Identifiable;

import java.time.Instant;
import java.util.List;

public class Delivery extends Base implements Identifiable<Delivery>, CreationTimestamped {
    private final String id;
    private final Instant createdAt;
    private final List<DeliveryItem> items;
    private final List<Parcel> parcels;

    private Delivery(final String id, final Instant createdAt, final List<DeliveryItem> items, final List<Parcel> parcels) {
        this.id = id;
        this.createdAt = createdAt;
        this.items = items;
        this.parcels = parcels;
    }

    public static Delivery of(final String id, final Instant createdAt, final List<DeliveryItem> items, final List<Parcel> parcels) {
        return new Delivery(id, createdAt, items, parcels);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<DeliveryItem> getItems() {
        return items;
    }

    public List<Parcel> getParcels() {
        return parcels;
    }
}
