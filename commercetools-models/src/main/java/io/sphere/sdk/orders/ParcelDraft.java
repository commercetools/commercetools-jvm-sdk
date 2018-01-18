package io.sphere.sdk.orders;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

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

    static ParcelDraft of(final ParcelMeasurements measurements, final TrackingData trackingData) {
        return new ParcelDraftDsl(null ,measurements, trackingData);
    }

    static ParcelDraft of(final ParcelMeasurements measurements) {
        return new ParcelDraftDsl(null, measurements, null);
    }

    static ParcelDraft of(final TrackingData trackingData) {
        return new ParcelDraftDsl(null, null, trackingData);
    }
}
