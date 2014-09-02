package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @param <T> the result type of the attribute, e.g., {@link io.sphere.sdk.models.LocalizedString}
 *
 */
public interface AttributeMapper<T> {

    T parse(final JsonNode value);

    public static <T> AttributeMapper<T> of(final TypeReference<T> typeReference) {
        return new AttributeMapperImpl<>(typeReference);
    }
}
