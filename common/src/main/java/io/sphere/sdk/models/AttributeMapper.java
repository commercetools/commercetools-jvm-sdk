package io.sphere.sdk.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @param <T> the result type of the attribute, e.g., {@link io.sphere.sdk.models.LocalizedString}
 *
 */
public interface AttributeMapper<T> {
    public static AttributeMapper<Double> ofDouble() {
        return AttributeMapper.of(new TypeReference<Double>() {
            @Override
            public String toString() {
                return "TypeReference<Double>";
            }
        });
    }

    public static AttributeMapper<LocalizedString> ofLocalizedString() {
        return AttributeMapper.of(new TypeReference<LocalizedString>() {
            @Override
            public String toString() {
                return "TypeReference<LocalizedString>";
            }
        });
    }

    T parse(final JsonNode value);

    public static <T> AttributeMapper<T> of(final TypeReference<T> typeReference) {
        return new AttributeMapperImpl<>(typeReference);
    }
}
