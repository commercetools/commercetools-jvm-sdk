package io.sphere.sdk.attributes;


import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.JsonUtils;

public class AttributeDraft {
    private final String name;
    private final JsonNode value;

    private AttributeDraft(final String name, final JsonNode value) {
        this.name = name;
        this.value = value;
    }

    public static <T> AttributeDraft of(final String name, final T value) {
        final JsonNode jsonNode = JsonUtils.newObjectMapper().valueToTree(value);
        return new AttributeDraft(name, jsonNode);
    }

    public static AttributeDraft of(final String name, final JsonNode value) {
        return new AttributeDraft(name, value);
    }

    public static <T> AttributeDraft of(final NamedAttributeAccess<T> namesAccess, final T value) {
        final JsonNode jsonNode = namesAccess.getMapper().serialize(value);
        return of(namesAccess.getName(), jsonNode);
    }

    public String getName() {
        return name;
    }

    public JsonNode getValue() {
        return value;
    }
}
