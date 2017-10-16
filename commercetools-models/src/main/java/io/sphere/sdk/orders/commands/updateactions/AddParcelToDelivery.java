package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.orders.*;

import javax.annotation.Nullable;
import java.util.List;

/**
    Adds a parcel to a delivery.

    {@doc.gen intro}

    {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#addParcelToDelivery()}

 @see Delivery#getParcels()
 */
public final class AddParcelToDelivery extends UpdateActionImpl<Order> {
    private final String deliveryId;
    @Nullable
    private final ParcelMeasurements measurements;
    @Nullable
    private final TrackingData trackingData;
    @Nullable
    private List<DeliveryItem> items;


    private AddParcelToDelivery(final String deliveryId, @Nullable final ParcelMeasurements measurements, @Nullable final TrackingData trackingData, final @Nullable List<DeliveryItem> items) {
        super("addParcelToDelivery");
        this.deliveryId = deliveryId;
        this.measurements = measurements;
        this.trackingData = trackingData;
        this.items = items;
    }

    public static AddParcelToDelivery of(final String deliveryId, final ParcelDraft parcelDraft) {
        return new AddParcelToDelivery(deliveryId, parcelDraft.getMeasurements(), parcelDraft.getTrackingData(),null);
    }

    public static AddParcelToDelivery of(final Delivery delivery, final ParcelDraft parcelDraft) {
        return of(delivery.getId(), parcelDraft);
    }

    public static AddParcelToDelivery of(final String deliveryId, final ParcelDraft parcelDraft,final List<DeliveryItem> items) {
        return new AddParcelToDelivery(deliveryId, parcelDraft.getMeasurements(), parcelDraft.getTrackingData(),items);
    }

    public static AddParcelToDelivery of(final Delivery delivery, final ParcelDraft parcelDraft,final List<DeliveryItem> items) {
        return of(delivery.getId(), parcelDraft,items);
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    @Nullable
    public ParcelMeasurements getMeasurements() {
        return measurements;
    }

    @Nullable
    public TrackingData getTrackingData() {
        return trackingData;
    }

    @Nullable
    List<DeliveryItem> getItems(){
        return items;
    }
}
