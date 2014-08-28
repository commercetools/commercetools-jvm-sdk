package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.AttributeMapper;
import io.sphere.sdk.utils.JsonUtils;

@JsonDeserialize(as = AttributeImpl.class)
public interface Attribute {
    String getName();

    <T> T getValue(AttributeMapper<T> mapper);

    public static Attribute of(final String name, final Object value) {
        final JsonNode jsonNode = JsonUtils.newObjectMapper().valueToTree(value);
        return new AttributeImpl(name, jsonNode);
    }
}
