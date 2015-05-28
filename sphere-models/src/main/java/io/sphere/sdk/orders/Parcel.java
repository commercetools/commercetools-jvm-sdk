package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.CreationTimestamped;

import java.time.ZonedDateTime;
import java.util.Optional;

public class Parcel extends Base implements CreationTimestamped {
    private final String id;
    private final ZonedDateTime createdAt;
    private final Optional<ParcelMeasurements> measurements;
    private final Optional<TrackingData> trackingData;

    @JsonCreator
    private Parcel(final String id, final ZonedDateTime createdAt, final Optional<ParcelMeasurements> measurements, final Optional<TrackingData> trackingData) {
        this.id = id;
        this.createdAt = createdAt;
        this.measurements = measurements;
        this.trackingData = trackingData;
    }

    public static Parcel of(final String id, final ZonedDateTime createdAt, final Optional<ParcelMeasurements> measurements, final Optional<TrackingData> trackingData) {
        return new Parcel(id, createdAt, measurements, trackingData);
    }

    public String getId() {
        return id;
    }

    @Override
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Optional<ParcelMeasurements> getMeasurements() {
        return measurements;
    }

    public Optional<TrackingData> getTrackingData() {
        return trackingData;
    }
}
