package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.models.LocalizedString;

/**
 * Converts an attribute of a specific type to JSON and back.
 *
 * @param <T> the result type of the attribute, e.g., {@link LocalizedString}
 *
 * @see Attribute
 */
public interface AttributeMapper<T> {

    T deserialize(final JsonNode value);

    JsonNode serialize(final T value);

    static <T> AttributeMapper<T> of(final TypeReference<T> typeReference) {
        return new AttributeMapperImpl<>(typeReference);
    }
}
