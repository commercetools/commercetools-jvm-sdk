package io.sphere.sdk.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = EnumValueImpl.class)
public interface EnumValue extends WithKey {
    String getKey();

    String getLabel();

    /**
     *
     * @param key The key of the value used as a programmatic identifier, e.g. in facets &amp; filters.
     * @param label A descriptive label of the value.
     * @return EnumValue
     */
    static EnumValue of(final String key, final String label) {
        return new EnumValueImpl(key, label);
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
    static TypeReference<EnumValue> typeReference() {
        return new TypeReference<EnumValue>() {
            @Override
            public String toString() {
                return "TypeReference<EnumValue>";
            }
        };
    }
}
