package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.orders.DeliveryItem;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderShippingInfo;
import io.sphere.sdk.orders.ParcelDraft;

import java.util.Collections;
import java.util.List;

/**
 Adds a delivery.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandTest#addDelivery()}

 @see OrderShippingInfo#getDeliveries()
 @see io.sphere.sdk.orders.messages.DeliveryAddedMessage
 */
public final class AddDelivery extends UpdateActionImpl<Order> {
    private final List<DeliveryItem> items;
    private final List<ParcelDraft> parcels;


    private AddDelivery(final List<DeliveryItem> items, final List<ParcelDraft> parcels) {
        super("addDelivery");
        this.items = items;
        this.parcels = parcels;
    }

    public static AddDelivery of(final List<DeliveryItem> items, final List<ParcelDraft> parcels) {
        return new AddDelivery(items, parcels);
    }

    public static AddDelivery of(final List<DeliveryItem> items) {
        return of(items, Collections.<ParcelDraft>emptyList());
    }

    public List<DeliveryItem> getItems() {
        return items;
    }

    public List<ParcelDraft> getParcels() {
        return parcels;
    }
}
