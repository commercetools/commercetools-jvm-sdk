package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.producttypes.attributes.AttributeDefinition;
import io.sphere.sdk.utils.JsonUtils;

@JsonDeserialize(as = AttributeImpl.class)
public interface Attribute {
    String getName();

    <T> T getValue(AttributeMapper<T> mapper);

    public static Attribute of(final String name, final JsonNode jsonNode) {
        return new AttributeImpl(name, jsonNode);
    }

    public static Attribute of(final String name, final Object value) {
        final JsonNode jsonNode = JsonUtils.newObjectMapper().valueToTree(value);
        return of(name, jsonNode);
    }

    public static <M, T> Attribute of(final AttributeSetter<M, T> setter, final T value) {
        final JsonNode jsonNode = setter.getMapper().serialize(value);
        return of(setter.getName(), jsonNode);
    }

    //todo add example with optional as result to not separate result and guard
    default <R> AttributeExtraction<R> collect(AttributeDefinition attrDefinition) {
        return AttributeExtraction.<R>of(attrDefinition, this);
    }
}
