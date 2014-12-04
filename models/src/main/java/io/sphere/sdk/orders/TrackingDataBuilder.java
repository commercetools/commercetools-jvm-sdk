package io.sphere.sdk.orders;

import io.sphere.sdk.models.Builder;

import java.util.Optional;

public class TrackingDataBuilder implements Builder<TrackingData> {
    private Optional<String> trackingId = Optional.empty();
    private Optional<String> carrier = Optional.empty();
    private Optional<String> provider = Optional.empty();
    private Optional<String> providerTransaction = Optional.empty();
    private boolean isReturn = false;

    private TrackingDataBuilder() {
    }

    public static TrackingDataBuilder of() {
        return new TrackingDataBuilder();
    }

    public TrackingDataBuilder trackingId(final Optional<String> trackingId) {
        this.trackingId = trackingId;
        return this;
    }

    public TrackingDataBuilder trackingId(final String trackingId) {
        return trackingId(Optional.of(trackingId));
    }

    public TrackingDataBuilder carrier(final Optional<String> carrier) {
        this.carrier = carrier;
        return this;
    }

    public TrackingDataBuilder carrier(final String carrier) {
        return carrier(Optional.of(carrier));
    }

    public TrackingDataBuilder provider(final Optional<String> provider) {
        this.provider = provider;
        return this;
    }

    public TrackingDataBuilder provider(final String provider) {
        return provider(Optional.of(provider));
    }

    public TrackingDataBuilder providerTransaction(final Optional<String> providerTransaction) {
        this.providerTransaction = providerTransaction;
        return this;
    }

    public TrackingDataBuilder providerTransaction(final String providerTransaction) {
        return providerTransaction(Optional.of(providerTransaction));
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
