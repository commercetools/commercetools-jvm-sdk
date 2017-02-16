package io.sphere.sdk.models;

import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for concrete {@linkplain GeoJSON} sub types.
 */
public class GeoJSONTest {
    private static final String EXAMPLE_GEOJSON = "{\"type\":\"Point\",\"coordinates\":[100.0,0.0]}";
    private static final Point EXAMPLE_POINT = Point.of(100.0, 0.0);

    @Test
    public void serializePoint() throws Exception {
        final String asJson = SphereJsonUtils.toJsonString(EXAMPLE_POINT);

        assertThat(asJson).isEqualTo(EXAMPLE_GEOJSON);
    }

    @Test
    public void deserializePoint() throws Exception {
        Point point = (Point) SphereJsonUtils.readObject(EXAMPLE_GEOJSON, GeoJSON.class);

        assertThat(point).isEqualTo(EXAMPLE_POINT);
    }
}
