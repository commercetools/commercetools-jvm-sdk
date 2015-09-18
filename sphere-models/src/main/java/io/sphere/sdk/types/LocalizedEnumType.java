package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.LocalizedEnumValue;

import java.util.List;

public class LocalizedEnumType extends FieldTypeBase {
    private final List<LocalizedEnumValue> values;

    @JsonCreator
    private LocalizedEnumType(final List<LocalizedEnumValue> values) {
        this.values = values;
    }

    @JsonIgnore
    public static LocalizedEnumType of(final List<LocalizedEnumValue> values) {
        return new LocalizedEnumType(values);
    }

    public List<LocalizedEnumValue> getValues() {
        return values;
    }
}
