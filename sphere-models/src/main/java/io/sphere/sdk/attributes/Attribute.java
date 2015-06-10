package io.sphere.sdk.attributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.json.JsonUtils;

@JsonDeserialize(as = AttributeImpl.class)
public interface Attribute {
    String getName();

    <T> T getValue(final AttributeAccess<T> access);

    static Attribute of(final String name, final JsonNode jsonNode) {
        return new AttributeImpl(name, jsonNode);
    }

    static <T> Attribute of(final String name, final AttributeAccess<T> access, final T value) {
        return of(access.ofName(name), value);
    }

    static <T> Attribute of(final NamedAttributeAccess<T> namedAttributeAccess, final T value) {
        final String name = namedAttributeAccess.getName();
        //here is not the attributeMapper used to keep LocalizedEnum values which
        //are transformed to just the key so the attribute could not be read anymore
        final JsonNode jsonNode = JsonUtils.toJsonNode(value);
        return of(name, jsonNode);
    }

    //todo add example with optional as result to not separate result and guard
    default <R> AttributeExtraction<R> collect(AttributeDefinition attrDefinition) {
        return AttributeExtraction.<R>of(attrDefinition, this);
    }
}
