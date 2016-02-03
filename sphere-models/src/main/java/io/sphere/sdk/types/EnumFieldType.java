package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.EnumValue;

import java.util.List;

/**
 * @see Custom
 */
public final class EnumFieldType extends FieldTypeBase {
    private final List<EnumValue> values;

    @JsonCreator
    private EnumFieldType(final List<EnumValue> values) {
        this.values = values;
    }

    @JsonIgnore
    public static EnumFieldType of(final List<EnumValue> values) {
        return new EnumFieldType(values);
    }

    public List<EnumValue> getValues() {
        return values;
    }
}
