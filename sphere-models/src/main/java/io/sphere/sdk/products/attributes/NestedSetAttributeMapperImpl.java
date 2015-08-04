package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.products.AttributeContainer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

class NestedSetAttributeMapperImpl extends AttributeMapperImpl<Set<AttributeContainer>> {
    NestedSetAttributeMapperImpl() {
        super(new TypeReference<Set<AttributeContainer>>() {});
    }

    @Override
    public Set<AttributeContainer> deserialize(JsonNode value) {
        try {
            final Set<List<Attribute>> raw = mapper().readerFor(new TypeReference<Set<List<Attribute>>>() {}).readValue(value);

            return raw.stream().map(AttributeContainer::of).collect(toSet());
        } catch (final IOException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public JsonNode serialize(final Set<AttributeContainer> attributeContainer) {
        return mapper().valueToTree(attributeContainer.stream().map(AttributeContainer::getAttributes).collect(toSet()));
    }
}
