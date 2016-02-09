package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public final class ParcelMeasurementsImpl extends Base implements ParcelMeasurements {
    private final Integer heightInMillimeter;
    private final Integer lengthInMillimeter;
    private final Integer widthInMillimeter;
    private final Integer weightInGram;

    @JsonCreator
    ParcelMeasurementsImpl(final Integer lengthInMillimeter, final Integer widthInMillimeter, final Integer heightInMillimeter, final Integer weightInGram) {
        this.heightInMillimeter = heightInMillimeter;
        this.lengthInMillimeter = lengthInMillimeter;
        this.widthInMillimeter = widthInMillimeter;
        this.weightInGram = weightInGram;
    }

    @Override
    public Integer getHeightInMillimeter() {
        return heightInMillimeter;
    }

    @Override
    public Integer getLengthInMillimeter() {
        return lengthInMillimeter;
    }

    @Override
    public Integer getWidthInMillimeter() {
        return widthInMillimeter;
    }

    @Override
    public Integer getWeightInGram() {
        return weightInGram;
    }
}
