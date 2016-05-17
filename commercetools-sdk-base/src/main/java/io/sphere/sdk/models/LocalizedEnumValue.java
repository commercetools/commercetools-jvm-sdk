package io.sphere.sdk.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = LocalizedEnumValueImpl.class)
public interface LocalizedEnumValue extends WithKey {
    static LocalizedEnumValue of(final String key, final LocalizedString label) {
        return new LocalizedEnumValueImpl(key, label);
    }

    @Override
    String getKey();

    LocalizedString getLabel();

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
    static TypeReference<LocalizedEnumValue> typeReference() {
        return new TypeReference<LocalizedEnumValue>() {
            @Override
            public String toString() {
                return "TypeReference<LocalizedEnumValue>";
            }
        };
    }
}
