package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;

@JsonDeserialize(as = TrackingDataImpl.class)
public interface TrackingData {
    static TrackingData of() {
        return of(null, null, null, null, false);
    }

    static TrackingData of(final String trackingId, final String carrier, final String provider, final String providerTransaction, final boolean isReturn) {
        return new TrackingDataImpl(trackingId, carrier, provider, providerTransaction, isReturn);
    }

    @Nullable
    String getTrackingId();

    @Nullable
    String getCarrier();

    @Nullable
    String getProvider();

    @Nullable
    String getProviderTransaction();

    @JsonProperty("isReturn")
    Boolean isReturn();

    default TrackingData withTrackingId(@Nullable final String trackingId) {
        return TrackingDataBuilder.of(this).trackingId(trackingId).build();
    }

    default TrackingData withCarrier(@Nullable final String carrier) {
        return TrackingDataBuilder.of(this).carrier(carrier).build();
    }

    default TrackingData withProvider(@Nullable final String provider) {
        return TrackingDataBuilder.of(this).provider(provider).build();
    }

    default TrackingData withProviderTransaction(@Nullable final String providerTransaction) {
        return TrackingDataBuilder.of(this).providerTransaction(providerTransaction).build();
    }

    default TrackingData withIsReturn(final boolean isReturn) {
        return TrackingDataBuilder.of(this).isReturn(isReturn).build();
    }
}
