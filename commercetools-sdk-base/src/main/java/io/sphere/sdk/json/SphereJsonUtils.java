package io.sphere.sdk.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

/**
 * Public utility class to work with JSON from SPHERE.IO.
 *
 * <p>If an error occurs, the {@link JsonException} (a {@link RuntimeException}) will be thrown:</p>
 *
 * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#exceptionHandling()}
 *
 */
public final class SphereJsonUtils {
    private static final ObjectMapper objectMapper = newObjectMapper();

    private SphereJsonUtils() {
    }

    /**
     * Creates a new {@link ObjectMapper} which is configured for sphere projects.
     * @return new object mapper
     */
    public static ObjectMapper newObjectMapper() {
        return new ObjectMapper()
                .registerModule(new LocaleModule())
                .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
                .registerModule(new JavaTimeModule())
                .registerModule(new DateTimeDeserializationModule())
                .registerModule(new DateTimeSerializationModule())
                .registerModule(new JavaMoneyModule())
                .registerModule(new SphereEnumModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.USE_GETTERS_AS_SETTERS, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Converts a SPHERE.IO Java object to JSON as String.
     *
     * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#toJsonString()}
     *
     * @param value the object to convert
     * @return JSON string representation of the value
     */
    public static String toJsonString(final Object value) {
        return executing(() -> objectMapper.writeValueAsString(value));
    }

    /**
     * Converts a SPHERE.IO Java object to JSON as {@link JsonNode}.
     * <p>If {@code value} is of type String and contains JSON data, that will be ignored, {@code value} will be treated as just any String.
     * If you want to parse a JSON String to a JsonNode use {@link SphereJsonUtils#parse(java.lang.String)} instead.</p>
     *
     * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#toJsonNode()}
     *
     * @param value the object to convert
     * @return new json
     */
    public static JsonNode toJsonNode(final Object value) {
        return objectMapper.valueToTree(value);
    }

    /**
     * Parses a String containing JSON data and produces a {@link JsonNode}.
     *
     * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#parse()}
     *
     * @param jsonAsString json data
     * @return new JsonNode
     */
    public static JsonNode parse(final String jsonAsString) {
        return executing(() -> objectMapper.readTree(jsonAsString));
    }

    /**
     * Parses a byte array containing JSON data and produces a {@link JsonNode}.
     *
     * @param jsonAsBytes json data
     * @return new JsonNode
     */
    public static JsonNode parse(final byte[] jsonAsBytes) {
        return executing(() -> objectMapper.readTree(jsonAsBytes));
    }

    /** Pretty prints a given JSON string.
     *
     * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#prettyPrint()}
     *
     * @param json JSON code as String which should be formatted
     * @return <code>json</code> formatted
     */
    public static String prettyPrint(final String json) {
        return executing(() -> {
            final ObjectMapper jsonParser = new ObjectMapper();
            final JsonNode jsonTree = jsonParser.readValue(json, JsonNode.class);
            secure(jsonTree);
            final ObjectWriter writer = jsonParser.writerWithDefaultPrettyPrinter();
            return writer.writeValueAsString(jsonTree);
        });
    }

    public static String prettyPrint(final JsonNode jsonNode) {
        return prettyPrint(toJsonString(jsonNode));
    }

    /**
     *
     * Reads a UTF-8 JSON text file from the classpath of the current thread and transforms it into a Java object.
     *
     * @param resourcePath the path to the resource. Example: If the file is located in "src/test/resources/foo/bar/product.json" then the path should be "foo/bar/product.json"
     * @param typeReference the full generic type information about the object to create
     * @param <T> the type of the result
     * @return the created objected
     */
    public static <T> T readObjectFromResource(final String resourcePath, final TypeReference<T> typeReference) {
        return executing(() -> {
            final InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
            return objectMapper.readValue(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8.name()), typeReference);
        });
    }

    public static <T> T readObjectFromResource(final String resourcePath, final JavaType javaType) {
        return executing(() -> {
            final InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
            return objectMapper.readValue(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8.name()), javaType);
        });
    }

    public static <T> T readObjectFromResource(final String resourcePath, final Class<T> clazz) {
        final JavaType javaType = convertToJavaType(clazz);
        return readObjectFromResource(resourcePath, javaType);
    }

    /**
     * Reads a Java object from JSON data (String).
     *
     * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#readObjectFromJsonString()}
     *
     * @param jsonAsString the JSON data which represents sth. of type {@code <T>}
     * @param typeReference the full generic type information about the object to create
     * @param <T> the type of the result
     * @return the created objected
     */
    public static <T> T readObject(final String jsonAsString, final TypeReference<T> typeReference) {
        return executing(() -> objectMapper.readValue(jsonAsString, typeReference));
    }

    public static <T> T readObject(final String jsonAsString, final Class<T> clazz) {
        return executing(() -> objectMapper.readValue(jsonAsString, clazz));
    }

    /**
     * Reads a Java object from JsonNode data.
     *
     * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#readObjectFromJsonNode()}
     *
     * @param jsonNode the JSON data which represents sth. of type {@code <T>}
     * @param typeReference the full generic type information about the object to create
     * @param <T> the type of the result
     * @return the created objected
     */
    public static <T> T readObject(final JsonNode jsonNode, final TypeReference<T> typeReference) {
        return executing(() -> objectMapper.readerFor(typeReference).readValue(jsonNode));
    }

    /**
     * Reads a Java object from JsonNode data.
     *
     * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#readObjectFromJsonNodeWithClass()}
     *
     * @param jsonNode the JSON data which represents sth. of type {@code <T>}
     * @param clazz the class of the type to create
     * @param <T> the type of the result
     * @return the created objected
     */
    public static <T> T readObject(final JsonNode jsonNode, final Class<T> clazz) {
        return executing(() -> objectMapper.readerFor(clazz).readValue(jsonNode));
    }

    public static <T> T readObject(final JsonNode jsonNode, final JavaType javaType) {
        return executing(() -> objectMapper.readerFor(javaType).readValue(jsonNode));
    }

    /**
     * Reads a Java object from JSON string data encoded as UTF-8.
     *
     * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#readObjectFromJsonNodeWithClass()}
     *
     * @param jsonAsBytes the JSON data which represents sth. of type {@code <T>}
     * @param typeReference the full generic type information about the object to create
     * @param <T> the type of the result
     * @return the created objected
     */
    public static <T> T readObject(final byte[] jsonAsBytes, final TypeReference<T> typeReference) {
        return executing(() -> objectMapper.readValue(jsonAsBytes, typeReference));
    }

    public static <T> T readObject(final byte[] jsonAsBytes, final JavaType javaType) {
        return executing(() -> objectMapper.readValue(jsonAsBytes, javaType));
    }

    /**
     * Creates a new {@link ObjectNode} created by the SPHERE.IO object mapper.
     *
     * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#readObjectFromJsonNodeWithClass()}
     *
     * @return new node
     */
    public static ObjectNode newObjectNode() {
        return objectMapper.createObjectNode();
    }

    public static <T> JavaType convertToJavaType(final TypeReference<T> typeReference) {
        final TypeFactory typeFactory = TypeFactory.defaultInstance();
        return typeFactory.constructType(typeReference);
    }

    /** Very simple way to "erase" passwords -
     *  replaces all field values whose names contains {@code 'pass'} by {@code '**removed from output**'}. */
    private static JsonNode secure(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode)node;
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                if (field.getValue().isTextual() && (field.getKey().toLowerCase().contains("pass") || field.getKey().toLowerCase().contains("access_token"))) {
                    objectNode.put(field.getKey(), "**removed from output**");
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

    public static JavaType convertToJavaType(final Class<?> clazz) {
        final TypeFactory typeFactory = TypeFactory.defaultInstance();
        return typeFactory.uncheckedSimpleType(clazz);
    }

    @FunctionalInterface
    private interface SupplierThrowingIOException<T> {
        T get() throws IOException;
    }

    private static <T> T executing(final SupplierThrowingIOException<T> supplier) {
        try {
            return supplier.get();
        } catch (final IOException e) {
            throw new JsonException(e);
        }
    }
}
