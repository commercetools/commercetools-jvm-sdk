package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import java.util.Optional;

public class TrackingData extends Base {
    private final Optional<String> trackingId;
    private final Optional<String> carrier;
    private final Optional<String> provider;
    private final Optional<String> providerTransaction;
    private final boolean isReturn;

    @JsonCreator
    private TrackingData(final Optional<String> trackingId, final Optional<String> carrier, final Optional<String> provider, final Optional<String> providerTransaction, final boolean isReturn) {
        this.trackingId = trackingId;
        this.carrier = carrier;
        this.provider = provider;
        this.providerTransaction = providerTransaction;
        this.isReturn = isReturn;
    }

    public static TrackingData of() {
        return of(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), false);
    }

    public static TrackingData of(final Optional<String> trackingId, final Optional<String> carrier, final Optional<String> provider, final Optional<String> providerTransaction, final boolean isReturn) {
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

    public boolean isReturn() {
        return isReturn;
    }

    public TrackingData withTrackingId(final String trackingId) {
        return newBuilder().trackingId(trackingId).build();
    }

    public TrackingData withCarrier(final String carrier) {
        return newBuilder().carrier(carrier).build();
    }

    public TrackingData withProvider(final String provider) {
        return newBuilder().provider(provider).build();
    }

    public TrackingData withProviderTransaction(final String providerTransaction) {
        return newBuilder().providerTransaction(providerTransaction).build();
    }

    public TrackingData withIsReturn(final boolean isReturn) {
        return newBuilder().isReturn(isReturn).build();
    }

    private TrackingDataBuilder newBuilder() {
        return TrackingDataBuilder.of(this);
    }
}
