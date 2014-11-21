package io.sphere.sdk.orders;

import io.sphere.sdk.models.Base;

import java.util.Optional;

public class TrackingData extends Base {
    private final Optional<String> trackingId;
    private final Optional<String> carrier;
    private final Optional<String> provider;
    private final Optional<String> providerTransaction;
    private final Optional<Boolean> isReturn;

    private TrackingData(final Optional<String> trackingId, final Optional<String> carrier, final Optional<String> provider, final Optional<String> providerTransaction, final Optional<Boolean> isReturn) {
        this.trackingId = trackingId;
        this.carrier = carrier;
        this.provider = provider;
        this.providerTransaction = providerTransaction;
        this.isReturn = isReturn;
    }

    public static TrackingData of(final Optional<String> trackingId, final Optional<String> carrier, final Optional<String> provider, final Optional<String> providerTransaction, final Optional<Boolean> isReturn) {
        return new TrackingData(trackingId, carrier, provider, providerTransaction, isReturn);
    }

    public Optional<String> getTrackingId() {
        return trackingId;
    }

    public Optional<String> getCarrier() {
        return carrier;
    }

    public Optional<String> getProvider() {
        return provider;
    }

    public Optional<String> getProviderTransaction() {
        return providerTransaction;
    }

    public Optional<Boolean> getIsReturn() {
        return isReturn;
    }
}
