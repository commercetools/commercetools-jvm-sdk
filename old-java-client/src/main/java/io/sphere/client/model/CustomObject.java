package io.sphere.client.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sphere.internal.util.json.SphereObjectMapperFactory;

import java.io.IOException;

/**
 * An object that contains a JSON value.
 */
public class CustomObject {
    private int version;
    private String container;
    private String key;
    private JsonNode value;

    // for tests
    protected CustomObject(String container, String key, JsonNode value) {
        this.container = container;
        this.key = key;
        this.value = value;
    }

    // for JSON deserializer
    protected CustomObject() {}

    /**
     * @return The container that this custom object is being stored in.
     */
    public String getContainer() { return container; }

    /**
     * @return The key that uniquely identifies this custom object within its container.
     */
    public String getKey() { return key; }

    /** 
     * @return The value of the custom object.
     * */
    public JsonNode getValue() { return value; }

    /** 
     * @return The version of the CustomObject.
     * */
    public int getVersion() { return version; }

    /**
     * Tries to parse the JSON values into the specified type.
     * De-serialization can be influenced by adding Jackson annotations to your model class.
     * @throws IOException
     * @deprecated this method needs a more flexible replacement
     */
    @Deprecated
    public <T> T as(Class<T> clazz) throws IOException {
        ObjectMapper mapper = SphereObjectMapperFactory.newObjectMapper();
        return mapper.convertValue(value, clazz);
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
