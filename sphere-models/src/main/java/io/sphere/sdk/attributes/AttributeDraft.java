package io.sphere.sdk.attributes;


import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.JsonUtils;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedEnumValue;

public class AttributeDraft extends Base {
    private final String name;
    private final JsonNode value;

    private AttributeDraft(final String name, final JsonNode value) {
        this.name = name;
        this.value = value;
    }

    public static AttributeDraft of(final String name, final LocalizedEnumValue value) {
        return of(AttributeAccess.ofLocalizedEnumValue().ofName(name), value);
    }

    public static <T> AttributeDraft of(final String name, final T value) {
        final JsonNode jsonNode = JsonUtils.toJsonNode(value);
        return new AttributeDraft(name, jsonNode);
    }

    public static AttributeDraft of(final String name, final JsonNode value) {
        return new AttributeDraft(name, value);
    }

    public static <T> AttributeDraft of(final String name, final AttributeAccess<T> access, final T value) {
        return of(access.ofName(name), value);
    }

    public static <T> AttributeDraft of(final NamedAttributeAccess<T> namedAccess, final T value) {
        final JsonNode jsonNode = namedAccess.getMapper().serialize(value);
        return of(namedAccess.getName(), jsonNode);
    }

    public String getName() {
        return name;
    }

    public JsonNode getValue() {
        return value;
    }
}
