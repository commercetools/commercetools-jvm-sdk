package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

final class ParcelImpl extends Base implements Parcel {
    private final String id;
    private final ZonedDateTime createdAt;
    @Nullable
    private final ParcelMeasurements measurements;
    @Nullable
    private final TrackingData trackingData;

    @JsonCreator
    ParcelImpl(final String id, final ZonedDateTime createdAt, @Nullable final ParcelMeasurements measurements, @Nullable final TrackingData trackingData) {
        this.id = id;
        this.createdAt = createdAt;
        this.measurements = measurements;
        this.trackingData = trackingData;
    }

    public String getId() {
        return id;
    }

    @Override
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Nullable
    public ParcelMeasurements getMeasurements() {
        return measurements;
    }

    @Nullable
    public TrackingData getTrackingData() {
        return trackingData;
    }
}
