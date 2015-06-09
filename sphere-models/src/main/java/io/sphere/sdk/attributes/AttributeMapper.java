package io.sphere.sdk.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @param <T> the result type of the attribute, e.g., {@link io.sphere.sdk.models.LocalizedStrings}
 *
 */
public interface AttributeMapper<T> {

    T deserialize(final JsonNode value);

    JsonNode serialize(final T value);

    static <T> AttributeMapper<T> of(final TypeReference<T> typeReference) {
        return new AttributeMapperImpl<>(typeReference);
    }
}
