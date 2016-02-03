package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;

public final class LocalizedEnumValue extends Base implements WithKey {
    private final String key;
    private final LocalizedString label;

    @JsonCreator
    private LocalizedEnumValue(final String key, final LocalizedString label) {
        this.key = key;
        this.label = label;
    }

    public static LocalizedEnumValue of(final String key, final LocalizedString label) {
        return new LocalizedEnumValue(key, label);
    }

    @Override
    public String getKey() {
        return key;
    }

    public LocalizedString getLabel() {
        return label;
    }

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    public static TypeReference<LocalizedEnumValue> typeReference() {
        return new TypeReference<LocalizedEnumValue>() {
            @Override
            public String toString() {
                return "TypeReference<LocalizedEnumValue>";
            }
        };
    }
}
