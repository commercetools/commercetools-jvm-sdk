package io.sphere.sdk.orders;

import io.sphere.sdk.models.CreationTimestamped;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public interface Parcel extends CreationTimestamped {
    static Parcel of(final String id, final ZonedDateTime createdAt, @Nullable final ParcelMeasurements measurements, @Nullable final TrackingData trackingData) {
        return new ParcelImpl(id, createdAt, measurements, trackingData);
    }

    String getId();

    @Override
    ZonedDateTime getCreatedAt();

    @Nullable
    ParcelMeasurements getMeasurements();

    @Nullable
    TrackingData getTrackingData();
}
