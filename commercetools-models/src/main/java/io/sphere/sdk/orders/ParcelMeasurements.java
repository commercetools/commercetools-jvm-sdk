package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(as = ParcelMeasurementsImpl.class)
@ResourceValue
public interface ParcelMeasurements {
    Integer getHeightInMillimeter();

    Integer getLengthInMillimeter();

    Integer getWidthInMillimeter();

    Integer getWeightInGram();

    @Nullable
    List<DeliveryItem> getItems();

    @Deprecated
    static ParcelMeasurements of(final Integer lengthInMillimeter, final Integer widthInMillimeter, final Integer heightInMillimeter, final Integer weightInGram) {
        return new ParcelMeasurementsImpl(heightInMillimeter, null, lengthInMillimeter, weightInGram,widthInMillimeter);
    }

    static ParcelMeasurements of(final Integer heightInMillimeter, @Nullable final List<DeliveryItem> items, final Integer lengthInMillimeter, final Integer weightInGram, final Integer widthInMillimeter){
        return new ParcelMeasurementsImpl(heightInMillimeter, items, lengthInMillimeter, weightInGram,widthInMillimeter);
    }


}
