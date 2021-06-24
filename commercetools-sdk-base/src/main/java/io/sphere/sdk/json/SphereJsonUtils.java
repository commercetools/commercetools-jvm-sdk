package io.sphere.sdk.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.sphere.sdk.models.KeyReference;
import io.sphere.sdk.models.Reference;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Public utility class to work with JSON from the commercetools platform.
 * <p>
 * <p>If an error occurs, the {@link JsonException} (a {@link RuntimeException}) will be thrown:</p>
 * <p>
 * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#exceptionHandling()}
 */
public final class SphereJsonUtils {
    private static final ObjectMapper objectMapper = newObjectMapper();

    private SphereJsonUtils() {
    }

    /**
     * Creates a new {@link ObjectMapper} which is configured for sphere projects.
     *
     * @return new object mapper
     */
    public static ObjectMapper newObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        return configureObjectMapper(objectMapper);
    }

    /**
     * Configures an existing external Jackson {@link ObjectMapper} with all modules and settings required
     * to serialize and deserialize according to JVM SDK conventions and capabilities:
     * <ul>
     * <li>Jackson Parameter Names Module (requires Java compiler flag -parameters)</li>
     * <li>Jackson JavaTime Module</li>
     * <li>Jackson config: do not fail on unknown properties</li>
     * <li>Jackson config: do not fail on empty beans</li>
     * <li>Jackson config: do not use getters as setters</li>
     * <li>Jackson config: do not serialize null value properties</li>
     * <li>Serialize LinkedHashSet as Set</li>
     * <li>commercetools LocalizedString</li>
     * <li>commercetools Time, Date, DateTime</li>
     * <li>commercetools Money for Java MonetaryAmount</li>
     * <li>commercetools Enum</li>
     * </ul>
     *
     * @param objectMapper the object mapper to configure
     * @return the given object mapper with additional configuration and modules
     */
    public static ObjectMapper configureObjectMapper(final ObjectMapper objectMapper) {
        return objectMapper
                .registerModule(new LocaleModule())
                .registerModule(new ParameterNamesModule())
                .registerModule(new JavaTimeModule())
                .registerModule(new DateTimeDeserializationModule())
                .registerModule(new DateTimeSerializationModule())
                .registerModule(new JavaMoneyModule())
                .registerModule(new SphereEnumModule())
                .registerModule(new SimpleModule().addAbstractTypeMapping(Set.class, LinkedHashSet.class))
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(MapperFeature.USE_GETTERS_AS_SETTERS, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .addMixIn(Reference.class, ReferenceMixIn.class)
                .addMixIn(KeyReference.class, ReferenceMixIn.class);
    }

    /**
     * Converts a commercetools platform Java object to JSON as String (one liner).
     * <p>
     * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#toJsonString()}
     *
     * @param value the object to convert
     * @return JSON string representation of the value
     */
    public static String toJsonString(final Object value) {
        return executing(() -> objectMapper.writeValueAsString(value));
    }

    /**
     * Converts a commercetools platform Java object to JSON as String (pretty).
     * <p>
     * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#toPrettyJsonString()}
     *
     * @param value the object to convert
     * @return JSON string representation of the value
     */
    public static String toPrettyJsonString(final Object value) {
        return prettyPrint(toJsonString(value));
    }

    /**
     * Converts a commercetools platform Java object to JSON as {@link JsonNode}.
     * <p>If {@code value} is of type String and contains JSON data, that will be ignored, {@code value} will be treated as just any String.
     * If you want to parse a JSON String to a JsonNode use {@link SphereJsonUtils#parse(java.lang.String)} instead.</p>
     * <p>
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
     * <p>
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

    /**
     * Pretty prints a given JSON string.
     * <p>
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
     * Reads a UTF-8 JSON text file from the classpath of the current thread and transforms it into a Java object.
     *
     * @param resourcePath  the path to the resource. Example: If the file is located in "src/test/resources/foo/bar/product.json" then the path should be "foo/bar/product.json"
     * @param typeReference the full generic type information about the object to create
     * @param <T>           the type of the result
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
     * <p>
     * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#readObjectFromJsonString()}
     *
     * @param jsonAsString  the JSON data which represents sth. of type {@code <T>}
     * @param typeReference the full generic type information about the object to create
     * @param <T>           the type of the result
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
     * <p>
     * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#readObjectFromJsonNode()}
     *
     * @param jsonNode      the JSON data which represents sth. of type {@code <T>}
     * @param typeReference the full generic type information about the object to create
     * @param <T>           the type of the result
     * @return the created objected
     */
    public static <T> T readObject(final JsonNode jsonNode, final TypeReference<T> typeReference) {
        return executing(() -> objectMapper.readerFor(typeReference)
                .readValue(jsonNode));
    }

    /**
     * Reads a Java object from JsonNode data.
     * <p>
     * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#readObjectFromJsonNodeWithClass()}
     *
     * @param jsonNode the JSON data which represents sth. of type {@code <T>}
     * @param clazz    the class of the type to create
     * @param <T>      the type of the result
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
     * <p>
     * {@include.example io.sphere.sdk.json.SphereJsonUtilsTest#readObjectFromJsonNodeWithClass()}
     *
     * @param jsonAsBytes   the JSON data which represents sth. of type {@code <T>}
     * @param typeReference the full generic type information about the object to create
     * @param <T>           the type of the result
     * @return the created objected
     */
    public static <T> T readObject(final byte[] jsonAsBytes, final TypeReference<T> typeReference) {
        return executing(() -> objectMapper.readValue(jsonAsBytes, typeReference));
    }

    public static <T> T readObject(final byte[] jsonAsBytes, final JavaType javaType) {
        return executing(() -> objectMapper.readValue(jsonAsBytes, javaType));
    }

    /**
     * Creates a new {@link ObjectNode} created by the commercetools platform object mapper.
     * <p>
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

    public static JavaType createCustomObjectJavaType(Class<?> customObject, Class<?> param){
        return objectMapper.getTypeFactory().constructParametricType(customObject, param);
    }

    /**
     * Very simple way to "erase" passwords -
     * replaces all field values whose names contains {@code 'pass'} by {@code '**removed from output**'}.
     */
    private static JsonNode secure(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
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
            ArrayNode arrayNode = (ArrayNode) node;
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
