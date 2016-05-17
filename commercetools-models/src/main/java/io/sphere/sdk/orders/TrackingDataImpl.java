package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

final class TrackingDataImpl extends Base implements TrackingData {
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
    TrackingDataImpl(@Nullable final String trackingId, @Nullable final String carrier, @Nullable final String provider, @Nullable final String providerTransaction, @Nullable final Boolean isReturn) {
        this.trackingId = trackingId;
        this.carrier = carrier;
        this.provider = provider;
        this.providerTransaction = providerTransaction;
        this.isReturn = isReturn;
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
}
