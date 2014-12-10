package io.sphere.sdk.orders;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.CreationTimestamped;

import java.time.Instant;
import java.util.Optional;

public class Parcel extends Base implements CreationTimestamped {
    private final String id;
    private final Instant createdAt;
    private final Optional<ParcelMeasurements> measurements;
    private final Optional<TrackingData> trackingData;

    private Parcel(final String id, final Instant createdAt, final Optional<ParcelMeasurements> measurements, final Optional<TrackingData> trackingData) {
        this.id = id;
        this.createdAt = createdAt;
        this.measurements = measurements;
        this.trackingData = trackingData;
    }

    public static Parcel of(final String id, final Instant createdAt, final Optional<ParcelMeasurements> measurements, final Optional<TrackingData> trackingData) {
        return new Parcel(id, createdAt, measurements, trackingData);
    }

    public String getId() {
        return id;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }

    public Optional<ParcelMeasurements> getMeasurements() {
        return measurements;
    }

    public Optional<TrackingData> getTrackingData() {
        return trackingData;
    }
}
