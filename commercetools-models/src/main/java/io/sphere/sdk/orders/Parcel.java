package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.CreationTimestamped;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@JsonDeserialize(as = ParcelImpl.class)
@ResourceValue
public interface Parcel extends CreationTimestamped {
    @Deprecated
    static Parcel of(final String id, final ZonedDateTime createdAt, @Nullable final ParcelMeasurements measurements, @Nullable final TrackingData trackingData) {
        return of(createdAt,id,null, measurements,trackingData);
    }

    static Parcel of(final ZonedDateTime createdAt, final String id,@Nullable final List<DeliveryItem> items, @Nullable final ParcelMeasurements measurements,@Nullable final TrackingData trackingData){
        return new ParcelImpl(createdAt,id,items, measurements,trackingData);
    }

    String getId();

    @Override
    ZonedDateTime getCreatedAt();

    @Nullable
    ParcelMeasurements getMeasurements();

    @Nullable
    TrackingData getTrackingData();

    @Nullable
    List<DeliveryItem> getItems();
}
