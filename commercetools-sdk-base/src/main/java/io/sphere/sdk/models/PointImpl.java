package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

final class PointImpl extends Base implements Point {
    private final List<Double>  coordinates;

    @JsonCreator
    PointImpl(@JsonProperty("coordinates") final List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    @Override
    @JsonIgnore
    public Double getLongitude() {
        return coordinates.get(0);
    }

    @Override
    @JsonIgnore
    public Double getLatitude() {
        return coordinates.get(1);
    }
}
