package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.HasUpdateAction;
import io.sphere.sdk.annotations.PropertySpec;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.CreationTimestamped;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@JsonDeserialize(as = ParcelImpl.class)
@ResourceValue
public interface Parcel extends CreationTimestamped {
    @Deprecated
    static Parcel of(final String id, final ZonedDateTime createdAt, @Nullable final ParcelMeasurements measurements, @Nullable final TrackingData trackingData) {
        return of(createdAt, id,null, measurements, trackingData);
    }

    static Parcel of(final ZonedDateTime createdAt, final String id, @Nullable final List<DeliveryItem> items, @Nullable final ParcelMeasurements measurements,@Nullable final TrackingData trackingData){
        return new ParcelImpl(createdAt, null, id, items, measurements, trackingData);
    }

    static Parcel of(final ZonedDateTime createdAt, final String id, @Nullable final CustomFields custom, @Nullable final List<DeliveryItem> items, @Nullable final ParcelMeasurements measurements,@Nullable final TrackingData trackingData){
        return new ParcelImpl(createdAt, custom, id, items, measurements,trackingData);
    }

    String getId();

    @Override
    ZonedDateTime getCreatedAt();

    @HasUpdateAction(value = "setParcelMeasurements", fields = {
            @PropertySpec(name = "parcelId", type = String.class),
            @PropertySpec(name = "measurements", type = ParcelMeasurements.class, isOptional = true)
    })
    @Nullable
    ParcelMeasurements getMeasurements();

    @HasUpdateAction(value = "setParcelTrackingData", fields = {
            @PropertySpec(name = "parcelId", type = String.class),
            @PropertySpec(name = "trackingData", type = TrackingData.class, isOptional = true)
    })
    @Nullable
    TrackingData getTrackingData();

    @HasUpdateAction(value = "setParcelItems", fields = {
            @PropertySpec(name = "parcelId", type = String.class),
            @PropertySpec(name = "items", type = DeliveryItem[].class, isOptional = true)
    })
    @Nullable
    List<DeliveryItem> getItems();

    @Nullable
    CustomFields getCustom();

    static String referenceTypeId() {
        return "order-parcel";
    }
}
