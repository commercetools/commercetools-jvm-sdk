package io.sphere.sdk.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.guava.GuavaModule;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

final public class JsonUtils {
    private static ObjectMapper objectMapper = newObjectMapper();

    private JsonUtils() {
    }

    public static ObjectMapper newObjectMapper() {
        return new ObjectMapper().registerModule(new GuavaModule()).registerModule(new Iso8601DateTimeJacksonModule());
    }

    public static String toJson(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /** Pretty prints given JSON string, replacing passwords by {@code 'xxxxx'}. */
    public static String prettyPrintJsonStringSecure(String json) throws IOException {
        ObjectMapper jsonParser = new ObjectMapper();
        JsonNode jsonTree = jsonParser.readValue(json, JsonNode.class);
        secure(jsonTree);
        ObjectWriter writer = jsonParser.writerWithDefaultPrettyPrinter();
        return writer.writeValueAsString(jsonTree);
    }

    /** Very simple way to "erase" passwords -
     *  replaces all field values whose names contains {@code 'pass'} by {@code 'xxxxx'}. */
    private static JsonNode secure(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode)node;
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                if (field.getValue().isTextual() && field.getKey().toLowerCase().contains("pass")) {
                    objectNode.put(field.getKey(), "xxxxx");
                } else {
                    secure(field.getValue());
                }
            }
            return objectNode;
        } else if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode)node;
            Iterator<JsonNode> elements = arrayNode.elements();
            while (elements.hasNext()) {
                secure(elements.next());
            }
            return arrayNode;
        } else {
            return node;
        }
    }
}
