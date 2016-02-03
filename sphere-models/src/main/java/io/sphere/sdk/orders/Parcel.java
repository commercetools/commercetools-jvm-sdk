package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.CreationTimestamped;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public final class Parcel extends Base implements CreationTimestamped {
    private final String id;
    private final ZonedDateTime createdAt;
    @Nullable
    private final ParcelMeasurements measurements;
    @Nullable
    private final TrackingData trackingData;

    @JsonCreator
    private Parcel(final String id, final ZonedDateTime createdAt, @Nullable final ParcelMeasurements measurements, @Nullable final TrackingData trackingData) {
        this.id = id;
        this.createdAt = createdAt;
        this.measurements = measurements;
        this.trackingData = trackingData;
    }

    public static Parcel of(final String id, final ZonedDateTime createdAt, @Nullable final ParcelMeasurements measurements, @Nullable final TrackingData trackingData) {
        return new Parcel(id, createdAt, measurements, trackingData);
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
