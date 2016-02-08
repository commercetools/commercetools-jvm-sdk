package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public final class ParcelMeasurements extends Base {
    private final Integer heightInMillimeter;
    private final Integer lengthInMillimeter;
    private final Integer widthInMillimeter;
    private final Integer weightInGram;

    @JsonCreator
    private ParcelMeasurements(final Integer lengthInMillimeter, final Integer widthInMillimeter, final Integer heightInMillimeter, final Integer weightInGram) {
        this.heightInMillimeter = heightInMillimeter;
        this.lengthInMillimeter = lengthInMillimeter;
        this.widthInMillimeter = widthInMillimeter;
        this.weightInGram = weightInGram;
    }

    public static ParcelMeasurements of(final Integer lengthInMillimeter, final Integer widthInMillimeter, final Integer heightInMillimeter, final Integer weightInGram) {
        return new ParcelMeasurements(lengthInMillimeter, widthInMillimeter, heightInMillimeter, weightInGram);
    }

    public Integer getHeightInMillimeter() {
        return heightInMillimeter;
    }

    public Integer getLengthInMillimeter() {
        return lengthInMillimeter;
    }

    public Integer getWidthInMillimeter() {
        return widthInMillimeter;
    }

    public Integer getWeightInGram() {
        return weightInGram;
    }
}
