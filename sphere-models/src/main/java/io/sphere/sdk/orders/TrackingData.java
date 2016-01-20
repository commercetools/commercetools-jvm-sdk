package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

public class TrackingData extends Base {
    @Nullable
    private final String trackingId;
    @Nullable
    private final String carrier;
    @Nullable
    private final String provider;
    @Nullable
    private final String providerTransaction;
    @Nullable
    private final Boolean isReturn;

    @JsonCreator
    private TrackingData(@Nullable final String trackingId, @Nullable final String carrier, @Nullable final String provider, @Nullable final String providerTransaction, @Nullable final Boolean isReturn) {
        this.trackingId = trackingId;
        this.carrier = carrier;
        this.provider = provider;
        this.providerTransaction = providerTransaction;
        this.isReturn = isReturn;
    }

    public static TrackingData of() {
        return of(null, null, null, null, false);
    }

    public static TrackingData of(final String trackingId, final String carrier, final String provider, final String providerTransaction, final boolean isReturn) {
        return new TrackingData(trackingId, carrier, provider, providerTransaction, isReturn);
    }

    @Nullable
    public String getTrackingId() {
        return trackingId;
    }

    @Nullable
    public String getCarrier() {
        return carrier;
    }

    @Nullable
    public String getProvider() {
        return provider;
    }

    @Nullable
    public String getProviderTransaction() {
        return providerTransaction;
    }

    @JsonProperty("isReturn")
    public Boolean isReturn() {
        return isReturn;
    }

    public TrackingData withTrackingId(@Nullable final String trackingId) {
        return newBuilder().trackingId(trackingId).build();
    }

    public TrackingData withCarrier(@Nullable final String carrier) {
        return newBuilder().carrier(carrier).build();
    }

    public TrackingData withProvider(@Nullable final String provider) {
        return newBuilder().provider(provider).build();
    }

    public TrackingData withProviderTransaction(@Nullable final String providerTransaction) {
        return newBuilder().providerTransaction(providerTransaction).build();
    }

    public TrackingData withIsReturn(final boolean isReturn) {
        return newBuilder().isReturn(isReturn).build();
    }

    private TrackingDataBuilder newBuilder() {
        return TrackingDataBuilder.of(this);
    }
}
