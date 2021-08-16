package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.models.WithKey;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

class EnumLikeSetAttributeMapperImpl<T extends WithKey> extends AttributeMapperImpl<Set<T>> {
    EnumLikeSetAttributeMapperImpl(final TypeReference<Set<T>> typeReference) {
        super(typeReference);
    }

    @Override
    public JsonNode serialize(final Set<T> t) {
        final Set<String> keySet = t.stream().map(elem -> elem.getKey()).collect(Collectors.toCollection(LinkedHashSet::new));
        return mapper().valueToTree(keySet);
    }
}
