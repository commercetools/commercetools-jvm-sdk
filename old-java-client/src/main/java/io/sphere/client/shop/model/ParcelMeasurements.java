package io.sphere.client.shop.model;

/**
 * Size and weight of a {@link io.sphere.client.shop.model.Parcel}.
 */
public class ParcelMeasurements {
    private int heightInMillimeter;
    private int lengthInMillimeter;
    private int widthInMillimeter;
    private int weightInGram;

    //for JSON mapper
    protected ParcelMeasurements() {
    }

    public ParcelMeasurements(int heightInMillimeter, int lengthInMillimeter, int widthInMillimeter, int weightInGram) {
        this.heightInMillimeter = heightInMillimeter;
        this.lengthInMillimeter = lengthInMillimeter;
        this.widthInMillimeter = widthInMillimeter;
        this.weightInGram = weightInGram;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParcelMeasurements that = (ParcelMeasurements) o;

        if (heightInMillimeter != that.heightInMillimeter) return false;
        if (lengthInMillimeter != that.lengthInMillimeter) return false;
        if (weightInGram != that.weightInGram) return false;
        if (widthInMillimeter != that.widthInMillimeter) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = heightInMillimeter;
        result = 31 * result + lengthInMillimeter;
        result = 31 * result + widthInMillimeter;
        result = 31 * result + weightInGram;
        return result;
    }

    @Override
    public String toString() {
        return "ParcelMeasurements{" +
                "heightInMillimeter=" + heightInMillimeter +
                ", lengthInMillimeter=" + lengthInMillimeter +
                ", widthInMillimeter=" + widthInMillimeter +
                ", weightInGram=" + weightInGram +
                '}';
    }
}
