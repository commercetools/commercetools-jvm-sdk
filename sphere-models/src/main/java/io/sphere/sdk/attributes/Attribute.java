package io.sphere.sdk.attributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.json.JsonUtils;

@JsonDeserialize(as = AttributeImpl.class)
public interface Attribute {
    String getName();

    <T> T getValue(AttributeMapper<T> mapper);

    JsonNode valueAsJson();

    static Attribute of(final String name, final JsonNode jsonNode) {
        return new AttributeImpl(name, jsonNode);
    }

    static Attribute of(final String name, final Object value) {
        final JsonNode jsonNode = JsonUtils.newObjectMapper().valueToTree(value);
        return of(name, jsonNode);
    }

    static <T> Attribute of(final AttributeSetter<T> setter, final T value) {
        final JsonNode jsonNode = setter.getMapper().serialize(value);
        return of(setter.getName(), jsonNode);
    }

    //todo add example with optional as result to not separate result and guard
    default <R> AttributeExtraction<R> collect(AttributeDefinition attrDefinition) {
        return AttributeExtraction.<R>of(attrDefinition, this);
    }
}
