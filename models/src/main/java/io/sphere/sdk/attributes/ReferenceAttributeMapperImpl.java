package io.sphere.sdk.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.models.Reference;

import java.util.Optional;

class ReferenceAttributeMapperImpl<T extends Reference<?>> extends AttributeMapperImpl<T> {
    ReferenceAttributeMapperImpl(final TypeReference<T> typeReference) {
        super(typeReference);
    }

    @Override
    public JsonNode serialize(final T t) {
        return mapper().valueToTree(t.toReference().filled(Optional.empty()));
    }
}
