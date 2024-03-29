package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.orders.DeliveryItem;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderShippingInfo;
import io.sphere.sdk.orders.ParcelDraft;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 Adds a delivery.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#addDelivery()}

 @see OrderShippingInfo#getDeliveries()
 @see io.sphere.sdk.orders.messages.DeliveryAddedMessage
 */
public final class AddDelivery extends UpdateActionImpl<Order> {
    private final List<DeliveryItem> items;
    private final List<ParcelDraft> parcels;
    @Nullable
    private final Address address;
    @Nullable
    private final CustomFields custom;

    private AddDelivery(final List<DeliveryItem> items, final List<ParcelDraft> parcels, @Nullable final Address address, @Nullable final CustomFields custom) {
        super("addDelivery");
        this.items = items;
        this.parcels = parcels;
        this.address = address;
        this.custom = custom;
    }

    public static AddDelivery of(final List<DeliveryItem> items, final List<ParcelDraft> parcels) {
        return new AddDelivery(items, parcels,null, null);
    }

    public static AddDelivery of(final List<DeliveryItem> items) {
        return of(items, Collections.<ParcelDraft>emptyList());
    }

    public AddDelivery withAddress(@Nullable final Address address) {
        return new AddDelivery(items, parcels, address, custom);
    }

    public List<DeliveryItem> getItems() {
        return items;
    }

    public List<ParcelDraft> getParcels() {
        return parcels;
    }

    @Nullable
    public Address getAddress() {
        return address;
    }

    @Nullable
    public CustomFields getCustom() {
        return custom;
    }
}
