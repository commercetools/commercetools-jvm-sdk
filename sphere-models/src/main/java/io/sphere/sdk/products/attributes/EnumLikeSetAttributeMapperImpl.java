package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.models.WithKey;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

class EnumLikeSetAttributeMapperImpl<T extends WithKey> extends AttributeMapperImpl<Set<T>> {
    EnumLikeSetAttributeMapperImpl(final TypeReference<Set<T>> typeReference) {
        super(typeReference);
    }

    @Override
    public JsonNode serialize(final Set<T> t) {
        final Set<String> keySet = t.stream().map(elem -> elem.getKey()).collect(toSet());
        return mapper().valueToTree(keySet);
    }
}
