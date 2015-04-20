package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;

public class LocalizedEnumValue extends Base implements WithKey {
    private final String key;
    private final LocalizedStrings label;

    @JsonCreator
    private LocalizedEnumValue(final String key, final LocalizedStrings label) {
        this.key = key;
        this.label = label;
    }

    public static LocalizedEnumValue of(final String key, final LocalizedStrings label) {
        return new LocalizedEnumValue(key, label);
    }

    @Override
    public String getKey() {
        return key;
    }

    public LocalizedStrings getLabel() {
        return label;
    }

    public static TypeReference<LocalizedEnumValue> typeReference() {
        return new TypeReference<LocalizedEnumValue>() {
            @Override
            public String toString() {
                return "TypeReference<LocalizedEnumValue>";
            }
        };
    }
}
