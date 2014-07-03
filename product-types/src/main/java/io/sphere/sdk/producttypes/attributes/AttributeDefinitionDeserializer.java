package io.sphere.sdk.producttypes.attributes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import io.sphere.sdk.utils.JsonUtils;

import java.io.IOException;

import static io.sphere.sdk.utils.MapUtils.getOrThrow;

class AttributeDefinitionDeserializer extends JsonDeserializer<AttributeDefinition> {
    private static final ImmutableMap<String, Class<? extends AttributeDefinition>> nameClassMap =
    ImmutableMap.<String, Class<? extends AttributeDefinition>>builder().
            put("text", TextAttributeDefinition.class).
            put("ltext", LocalizedTextAttributeDefinition.class).
            put("enum", EnumAttributeDefinition.class).
            put("lenum", LocalizedEnumAttributeDefinition.class).
            put("number", NumberAttributeDefinition.class).
            put("money", MoneyAttributeDefinition.class).
            put("time", TimeAttributeDefinition.class).
            put("date", DateAttributeDefinition.class).
            put("datetime", DateTimeAttributeDefinition.class).
            build();

    @Override
    public AttributeDefinition deserialize(final JsonParser jsonParser, final DeserializationContext context) throws IOException {
        final JsonNode jsonNode = jsonParser.<JsonNode>readValueAsTree();
        final String name = extractNameFromJson(jsonNode);
        final Class<? extends AttributeDefinition> clazz = findClassForTypeName(name);
        //the jsonParser is not reusable after reading a value from it, so an object mapper needs to transform the JSON
        return JsonUtils.newObjectMapper().treeToValue(jsonNode, clazz);
    }

    private Class<? extends AttributeDefinition> findClassForTypeName(final String name) throws IOException {
        return getOrThrow(nameClassMap, name, () -> new IOException("No mapping for " + name + " in " + AttributeDefinitionDeserializer.class.getCanonicalName()));
    }

    private String extractNameFromJson(final JsonNode jsonNode) {
        return jsonNode.get("type").get("name").asText();
    }
}
