package io.sphere.sdk.orders;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.annotation.Nullable;

public final class TrackingDataBuilder extends Base implements Builder<TrackingData> {
    @Nullable
    private String trackingId;
    @Nullable
    private String carrier;
    @Nullable
    private String provider;
    @Nullable
    private String providerTransaction;
    private Boolean isReturn = false;

    private TrackingDataBuilder() {
    }

    public static TrackingDataBuilder of() {
        return new TrackingDataBuilder();
    }

    public TrackingDataBuilder trackingId(@Nullable final String trackingId) {
        this.trackingId = trackingId;
        return this;
    }

    public TrackingDataBuilder carrier(@Nullable final String carrier) {
        this.carrier = carrier;
        return this;
    }


    public TrackingDataBuilder provider(@Nullable final String provider) {
        this.provider = provider;
        return this;
    }

    public TrackingDataBuilder providerTransaction(@Nullable final String providerTransaction) {
        this.providerTransaction = providerTransaction;
        return this;
    }

    public TrackingDataBuilder isReturn(final boolean isReturn) {
        this.isReturn = isReturn;
        return this;
    }

    @Override
    public TrackingData build() {
        return TrackingData.of(trackingId, carrier, provider, providerTransaction, isReturn);
    }

    public static TrackingDataBuilder of(final TrackingData template) {
        return TrackingDataBuilder.of()
                .trackingId(template.getTrackingId())
                .carrier(template.getCarrier())
                .provider(template.getProvider())
                .providerTransaction(template.getProviderTransaction())
                .isReturn(template.isReturn());
    }
}
