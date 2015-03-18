package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public class ParcelMeasurements extends Base {
    private final int heightInMillimeter;
    private final int lengthInMillimeter;
    private final int widthInMillimeter;
    private final int weightInGram;

    @JsonCreator
    private ParcelMeasurements(final int lengthInMillimeter, final int widthInMillimeter, final int heightInMillimeter, final int weightInGram) {
        this.heightInMillimeter = heightInMillimeter;
        this.lengthInMillimeter = lengthInMillimeter;
        this.widthInMillimeter = widthInMillimeter;
        this.weightInGram = weightInGram;
    }

    public static ParcelMeasurements of(final int lengthInMillimeter, final int widthInMillimeter, final int heightInMillimeter, final int weightInGram) {
        return new ParcelMeasurements(lengthInMillimeter, widthInMillimeter, heightInMillimeter, weightInGram);
    }

    public int getHeightInMillimeter() {
        return heightInMillimeter;
    }

    public int getLengthInMillimeter() {
        return lengthInMillimeter;
    }

    public int getWidthInMillimeter() {
        return widthInMillimeter;
    }

    public int getWeightInGram() {
        return weightInGram;
    }
}
