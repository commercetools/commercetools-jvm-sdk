package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.products.AttributeContainer;

import java.io.IOException;
import java.util.List;

class NestedAttributeMapperImpl extends AttributeMapperImpl<AttributeContainer> {
    NestedAttributeMapperImpl() {
        super(new TypeReference<AttributeContainer>() {});
    }

    @Override
    public AttributeContainer deserialize(JsonNode value) {
        try {
            return  AttributeContainer.of(mapper().readerFor(new TypeReference<List<Attribute>>() {}).readValue(value));
        } catch (final IOException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public JsonNode serialize(final AttributeContainer attributeContainer) {
        return mapper().valueToTree(attributeContainer.getAttributes());
    }
}
