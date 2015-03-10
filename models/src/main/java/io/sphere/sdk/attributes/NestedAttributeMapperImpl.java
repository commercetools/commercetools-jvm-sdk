package io.sphere.sdk.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.models.Reference;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

class NestedAttributeMapperImpl extends AttributeMapperImpl<AttributeContainer> {
    NestedAttributeMapperImpl() {
        super(new TypeReference<AttributeContainer>() {});
    }

    @Override
    public AttributeContainer deserialize(JsonNode value) {
        try {
            return  AttributeContainer.of(mapper().reader(new TypeReference<List<Attribute>>() {}).readValue(value));
        } catch (final IOException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public JsonNode serialize(final AttributeContainer attributeContainer) {
        return mapper().valueToTree(attributeContainer.getAttributes());
    }
}
