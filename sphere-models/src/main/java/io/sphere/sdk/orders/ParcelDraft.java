package io.sphere.sdk.orders;

import javax.annotation.Nullable;

public interface ParcelDraft {
    @Nullable
    ParcelMeasurements getMeasurements();

    @Nullable
    TrackingData getTrackingData();

    static ParcelDraft of(final ParcelMeasurements measurements, final TrackingData trackingData) {
        return new ParcelDraftImpl(measurements, trackingData);
    }

    static ParcelDraft of(final ParcelMeasurements measurements) {
        return new ParcelDraftImpl(measurements, null);
    }

    static ParcelDraft of(final TrackingData trackingData) {
        return new ParcelDraftImpl(null, trackingData);
    }
}
