package io.sphere.sdk.orders;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.List;


@ResourceDraftValue(factoryMethods = {@FactoryMethod(parameterNames = {})})
public interface ParcelDraft {
    @Nullable
    ParcelMeasurements getMeasurements();

    @Nullable
    TrackingData getTrackingData();

    @Nullable
    List<DeliveryItem> getItems();

    @Nullable
    CustomFieldsDraft getCustom();

    static ParcelDraft of(final ParcelMeasurements measurements, final TrackingData trackingData) {
        return new ParcelDraftDsl(null, null, measurements, trackingData);
    }

    static ParcelDraft of(final ParcelMeasurements measurements) {
        return new ParcelDraftDsl(null, null, measurements, null);
    }

    static ParcelDraft of(final TrackingData trackingData) {
        return new ParcelDraftDsl(null, null, null, trackingData);
    }

    static ParcelDraft of(final TrackingData trackingData, final List<DeliveryItem> deliveryItems) { 
        return new ParcelDraftDsl(null, deliveryItems, null, trackingData);
    }

    static ParcelDraft of(final TrackingData trackingData, final List<DeliveryItem> deliveryItems, final CustomFieldsDraft custom) {
        return new ParcelDraftDsl(custom, deliveryItems, null, trackingData);
    }
}
