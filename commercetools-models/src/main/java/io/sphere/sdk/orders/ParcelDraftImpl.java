package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.List;

final class ParcelDraftImpl extends Base implements ParcelDraft {
    @Nullable
    private final ParcelMeasurements measurements;
    @Nullable
    private final TrackingData trackingData;

    @Nullable
    private final List<DeliveryItem> items;

    @JsonCreator
    ParcelDraftImpl(@Nullable final ParcelMeasurements measurements, @Nullable final TrackingData trackingData, @Nullable final List<DeliveryItem> items) {
        this.measurements = measurements;
        this.trackingData = trackingData;
        this.items = items;
    }

    @Override
    @Nullable
    public ParcelMeasurements getMeasurements() {
        return measurements;
    }

    @Override
    @Nullable
    public TrackingData getTrackingData() {
        return trackingData;
    }

    @Nullable
    @Override
    public List<DeliveryItem> getItems() {
        return items;
    }
}
