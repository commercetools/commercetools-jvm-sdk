package io.sphere.sdk.orders;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

public class ParcelDraft extends Base {
    @Nullable
    private final ParcelMeasurements measurements;
    @Nullable
    private final TrackingData trackingData;

    private ParcelDraft(@Nullable final ParcelMeasurements measurements, @Nullable final TrackingData trackingData) {
        this.measurements = measurements;
        this.trackingData = trackingData;
    }

    public static ParcelDraft of(final ParcelMeasurements measurements, final TrackingData trackingData) {
        return new ParcelDraft(measurements, trackingData);
    }

    public static ParcelDraft of(final ParcelMeasurements measurements) {
        return new ParcelDraft(measurements, null);
    }

    public static ParcelDraft of(final TrackingData trackingData) {
        return new ParcelDraft(null, trackingData);
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
