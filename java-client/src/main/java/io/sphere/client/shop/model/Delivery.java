package io.sphere.client.shop.model;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * A Delivery contains a shipment of line items or custom line items with an arbitrary number of parcels.
 */
public final class Delivery {
    @Nonnull private String id = "";
    @Nonnull private DateTime createdAt;

    /** Collects the ids and quantities of shipped line items and custom line items. */
    @Nonnull private List<DeliveryItem> items = Lists.newArrayList();

    /** The parcels that contains the items. */
    @Nonnull private List<Parcel> parcels = Lists.newArrayList();

    //for JSON mapper
    protected Delivery() {
    }

    @Nonnull
    public String getId() {
        return id;
    }

    @Nonnull
    public DateTime getCreatedAt() {
        return createdAt;
    }

    @Nonnull
    public List<DeliveryItem> getItems() {
        return items;
    }

    @Nonnull
    public List<Parcel> getParcels() {
        return parcels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Delivery delivery = (Delivery) o;

        if (!createdAt.equals(delivery.createdAt)) return false;
        if (!id.equals(delivery.id)) return false;
        if (!items.equals(delivery.items)) return false;
        if (!parcels.equals(delivery.parcels)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + items.hashCode();
        result = 31 * result + parcels.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", items=" + items +
                ", parcels=" + parcels +
                '}';
    }
}
