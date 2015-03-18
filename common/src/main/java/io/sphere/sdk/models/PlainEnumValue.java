package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;

public class PlainEnumValue extends Base implements WithKey {
    private final String key;
    private final String label;

    @JsonCreator
    private PlainEnumValue(final String key, final String label) {
        this.key = key;
        this.label = label;
    }

    @Override
    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    /**
     *
     * @param key The key of the value used as a programmatic identifier, e.g. in facets &amp; filters.
     * @param label A descriptive label of the value.
     * @return PlainEnumValue
     */
    public static PlainEnumValue of(final String key, final String label) {
        return new PlainEnumValue(key, label);
    }

    public static TypeReference<PlainEnumValue> typeReference() {
        return new TypeReference<PlainEnumValue>() {
            @Override
            public String toString() {
                return "TypeReference<PlainEnumValue>";
            }
        };
    }
}
