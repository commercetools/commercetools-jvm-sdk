package io.sphere.sdk.orders;

public class ParcelMeasurements {
    private final int heightInMillimeter;
    private final int lengthInMillimeter;
    private final int widthInMillimeter;
    private final int weightInGram;

    private ParcelMeasurements(final int heightInMillimeter, final int lengthInMillimeter, final int widthInMillimeter, final int weightInGram) {
        this.heightInMillimeter = heightInMillimeter;
        this.lengthInMillimeter = lengthInMillimeter;
        this.widthInMillimeter = widthInMillimeter;
        this.weightInGram = weightInGram;
    }

    public static ParcelMeasurements of(final int heightInMillimeter, final int lengthInMillimeter, final int widthInMillimeter, final int weightInGram) {
        return new ParcelMeasurements(heightInMillimeter, lengthInMillimeter, widthInMillimeter, weightInGram);
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
