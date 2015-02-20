package io.sphere.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.sphere.sdk.exceptions.JsonException;
import org.zapodot.jackson.java8.JavaOptionalModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

final public class JsonUtils {
    private static final ObjectMapper objectMapper = newObjectMapper();

    private JsonUtils() {
    }

    public static ObjectMapper newObjectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaOptionalModule())
                .registerModule(new ParameterNamesModule())
                .registerModule(new JSR310Module())//Java 8 DateTime
                .registerModule(new DateTimeSerializationModule())
                .registerModule(new JavaMoneyModule())
                .registerModule(new SphereEnumModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJson(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            //to extend RuntimeException to be able to catch specific classes
            throw new JsonException(e);
        }
    }

    /** Pretty prints given JSON string, replacing passwords by {@code 'xxxxx'}.
     * @param json JSON code as String which should be formatted
     * @return <code>json</code> formatted
     */
    public static String prettyPrintJsonStringSecure(String json) {
        try {
            ObjectMapper jsonParser = new ObjectMapper();
            JsonNode jsonTree = jsonParser.readValue(json, JsonNode.class);
            secure(jsonTree);
            ObjectWriter writer = jsonParser.writerWithDefaultPrettyPrinter();
            return writer.writeValueAsString(jsonTree);
        } catch (IOException e) {
           throw new JsonException(e);
        }
    }

    /**
     * Works like {@link JsonUtils#prettyPrintJsonStringSecure(java.lang.String)} but returns the unparsed JSON string if the
     * formatting fails.
     * @param json the input json to format
     * @return the formatted json or in error cases the unformatted json
     */
    //TODO rename
    public static String prettyPrintJsonStringSecureWithFallback(String json) {
        String result = json;
        try {
            ObjectMapper jsonParser = new ObjectMapper();
            JsonNode jsonTree = jsonParser.readValue(json, JsonNode.class);
            secure(jsonTree);
            ObjectWriter writer = jsonParser.writerWithDefaultPrettyPrinter();
            result = writer.writeValueAsString(jsonTree);
        } catch (IOException e) {
            result = json;
        }
        return result;
    }

    public static <T> T readObjectFromResource(final String resourcePath, final TypeReference<T> typeReference) {
        try {
            final InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
            return objectMapper.readValue(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8.name()), typeReference);
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    public static <T> T readObjectFromJsonString(TypeReference<T> typeReference, String jsonAsString) {
        try {
            return objectMapper.readValue(jsonAsString, typeReference);
        } catch (IOException e) {
            throw new JsonException(e);//TODO improve exception
        }
    }

    public static <T> T readObject(final TypeReference<T> typeReference, final byte[] input) {
        try {
            return objectMapper.readValue(input, typeReference);
        } catch (IOException e) {
            throw new JsonException(e);
        }
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
