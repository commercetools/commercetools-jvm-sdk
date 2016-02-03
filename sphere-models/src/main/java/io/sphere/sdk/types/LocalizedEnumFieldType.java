package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.LocalizedEnumValue;

import java.util.List;

/**
 * @see Custom
 */
public final class LocalizedEnumFieldType extends FieldTypeBase {
    private final List<LocalizedEnumValue> values;

    @JsonCreator
    private LocalizedEnumFieldType(final List<LocalizedEnumValue> values) {
        this.values = values;
    }

    @JsonIgnore
    public static LocalizedEnumFieldType of(final List<LocalizedEnumValue> values) {
        return new LocalizedEnumFieldType(values);
    }

    public List<LocalizedEnumValue> getValues() {
        return values;
    }
}
