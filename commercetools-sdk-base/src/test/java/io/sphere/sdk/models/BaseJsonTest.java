package io.sphere.sdk.models;

import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseJsonTest {
    @Test
    public void testAdditionalProperties() throws Exception {
        final String json = "{\"coordinates\": [100.0,0.0], \"type\":\"Point\", \"foo\": \"bar\"}";

        Base point = (Base) SphereJsonUtils.readObject(json, GeoJSON.class);
        assertThat(point.additionalFields().get("foo").asText()).isEqualTo("bar");
    }
}
