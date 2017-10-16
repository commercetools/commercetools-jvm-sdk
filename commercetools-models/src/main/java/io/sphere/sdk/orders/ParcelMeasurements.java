package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(as = ParcelMeasurementsImpl.class)
public interface ParcelMeasurements {
    Integer getHeightInMillimeter();

    Integer getLengthInMillimeter();

    Integer getWidthInMillimeter();

    Integer getWeightInGram();

    @Nullable
    List<DeliveryItem> getItems();

    static ParcelMeasurements of(final Integer lengthInMillimeter, final Integer widthInMillimeter, final Integer heightInMillimeter, final Integer weightInGram) {
        return new ParcelMeasurementsImpl(lengthInMillimeter, widthInMillimeter, heightInMillimeter, weightInGram);
    }
}
