package io.sphere.sdk.models;


import java.util.Arrays;

/**
 * Represents a GeoJSON point.
 *
 * @see <a href="https://geojson.org/geojson-spec.html#point">GeoJSON Point</a>
 */
public interface Point extends GeoJSON {
    /**
     * Returns the longitude of this point.
     *
     * @return the longitude of this point
     */
    Double getLongitude();

    /**
     * Returns the latitude of this point.
     *
     * @return the latitude of this point
     */
    Double getLatitude();

    static Point of(final Double longitude, Double latitude) {
        return new PointImpl(Arrays.asList(longitude, latitude));
    }
}
