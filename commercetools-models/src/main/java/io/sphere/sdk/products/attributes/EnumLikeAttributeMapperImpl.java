package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.models.WithKey;

class EnumLikeAttributeMapperImpl<T extends WithKey> extends AttributeMapperImpl<T> {
    EnumLikeAttributeMapperImpl(final TypeReference<T> typeReference) {
        super(typeReference);
    }

    @Override
    public JsonNode serialize(final T t) {
        return mapper().valueToTree(t);
    }
}
