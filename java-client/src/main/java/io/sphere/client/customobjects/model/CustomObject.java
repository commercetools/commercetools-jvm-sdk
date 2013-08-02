package io.sphere.client.customobjects.model;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * An object that contains a JSON value.
 */
public class CustomObject {
    private String container;
    private String key;
    private JsonNode value;

    public CustomObject(String container, String key, JsonNode value) {
        this.container = container;
        this.key = key;
        this.value = value;
    }

    // for JSON deserializer
    protected CustomObject() {
    }

    public String getContainer() {
        return container;
    }

    public String getKey() {
        return key;
    }

    public JsonNode getValue() {
        return value;
    }

    public <T> T as(Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(value, clazz);
    }
}
