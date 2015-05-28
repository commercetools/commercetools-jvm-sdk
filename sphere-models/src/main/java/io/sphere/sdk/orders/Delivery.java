package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.CreationTimestamped;
import io.sphere.sdk.models.Identifiable;

import java.time.ZonedDateTime;
import java.util.List;

public class Delivery extends Base implements Identifiable<Delivery>, CreationTimestamped {
    private final String id;
    private final ZonedDateTime createdAt;
    private final List<DeliveryItem> items;
    private final List<Parcel> parcels;

    @JsonCreator
    private Delivery(final String id, final ZonedDateTime createdAt, final List<DeliveryItem> items, final List<Parcel> parcels) {
        this.id = id;
        this.createdAt = createdAt;
        this.items = items;
        this.parcels = parcels;
    }

    public static Delivery of(final String id, final ZonedDateTime createdAt, final List<DeliveryItem> items, final List<Parcel> parcels) {
        return new Delivery(id, createdAt, items, parcels);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public List<DeliveryItem> getItems() {
        return items;
    }

    public List<Parcel> getParcels() {
        return parcels;
    }
}
