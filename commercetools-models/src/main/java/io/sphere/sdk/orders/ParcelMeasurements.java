package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ParcelMeasurementsImpl.class)
public interface ParcelMeasurements {
    Integer getHeightInMillimeter();

    Integer getLengthInMillimeter();

    Integer getWidthInMillimeter();

    Integer getWeightInGram();

    static ParcelMeasurements of(final Integer lengthInMillimeter, final Integer widthInMillimeter, final Integer heightInMillimeter, final Integer weightInGram) {
        return new ParcelMeasurementsImpl(lengthInMillimeter, widthInMillimeter, heightInMillimeter, weightInGram);
    }
}
