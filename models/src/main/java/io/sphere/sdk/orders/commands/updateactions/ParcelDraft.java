package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.orders.ParcelMeasurements;
import io.sphere.sdk.orders.TrackingData;

import java.util.Optional;

public class ParcelDraft extends Base {
    private final Optional<ParcelMeasurements> measurements;
    private final Optional<TrackingData> trackingData;

    private ParcelDraft(final Optional<ParcelMeasurements> measurements, final Optional<TrackingData> trackingData) {
        this.measurements = measurements;
        this.trackingData = trackingData;
    }

    public static ParcelDraft of(final Optional<ParcelMeasurements> measurements, final Optional<TrackingData> trackingData) {
        return new ParcelDraft(measurements, trackingData);
    }

    public static ParcelDraft of(final ParcelMeasurements measurements, final TrackingData trackingData) {
        return new ParcelDraft(Optional.of(measurements), Optional.of(trackingData));
    }

    public static ParcelDraft of(final ParcelMeasurements measurements) {
        return new ParcelDraft(Optional.of(measurements), Optional.empty());
    }

    public static ParcelDraft of(final TrackingData trackingData) {
        return new ParcelDraft(Optional.empty(), Optional.of(trackingData));
    }

    public Optional<ParcelMeasurements> getMeasurements() {
        return measurements;
    }

    public Optional<TrackingData> getTrackingData() {
        return trackingData;
    }
}
