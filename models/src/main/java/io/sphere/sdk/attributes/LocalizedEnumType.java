package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.LocalizedEnumValue;

import java.util.List;

public class LocalizedEnumType extends AttributeTypeBase {
    private final List<LocalizedEnumValue> values;

    private LocalizedEnumType(final List<LocalizedEnumValue> values) {
        this.values = values;
    }

    public List<LocalizedEnumValue> getValues() {
        return values;
    }

    @JsonIgnore
    public static LocalizedEnumType of(final List<LocalizedEnumValue> values) {
        return new LocalizedEnumType(values);
    }
}
