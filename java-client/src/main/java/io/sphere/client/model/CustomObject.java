package io.sphere.client.model;

import io.sphere.internal.command.Command;
import io.sphere.internal.command.PutCustomObjectCommand;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * An object that contains a JSON value.
 */
public class CustomObject implements PutCustomObjectCommand {
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

    /**
     * @return The container that this custom object is being stored in.
     */
    public String getContainer() {
        return container;
    }

    /**
     * @return The key that uniquely identifies this custom object within its container.
     */
    public String getKey() {
        return key;
    }

    public JsonNode getValue() {
        return value;
    }

    /**
     * Tries to parse the JSON values into the specified type.
     * De-serialization can be influenced by adding Jackson annotations to your model class.
     * @throws IOException
     */
    public <T> T as(Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(value, clazz);
    }

    @Override
    public String toString(){
        return String.format("[%s/%s value:%s]", container, key, "" + value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomObject that = (CustomObject) o;

        if (!container.equals(that.container)) return false;
        if (!key.equals(that.key)) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = container.hashCode();
        result = 31 * result + key.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
