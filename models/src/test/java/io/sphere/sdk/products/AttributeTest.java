package io.sphere.sdk.products;

import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.json.JsonUtils;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class AttributeTest {

    @Test
    public void serializeStringAttribute() throws Exception {
        final String json = JsonUtils.toJson(Attribute.of("size", "M"));
        assertThat(json).isEqualTo("{\"name\":\"size\",\"value\":\"M\"}");
    }
}