package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

final class ParcelDraftImpl extends Base implements ParcelDraft {
    @Nullable
    private final ParcelMeasurements measurements;
    @Nullable
    private final TrackingData trackingData;

    @JsonCreator
    ParcelDraftImpl(@Nullable final ParcelMeasurements measurements, @Nullable final TrackingData trackingData) {
        this.measurements = measurements;
        this.trackingData = trackingData;
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
}
