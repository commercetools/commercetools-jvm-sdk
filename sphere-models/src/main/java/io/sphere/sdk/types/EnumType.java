package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.EnumValue;

import java.util.List;

public class EnumType extends FieldTypeBase {
    private final List<EnumValue> values;

    @JsonCreator
    private EnumType(final List<EnumValue> values) {
        this.values = values;
    }

    @JsonIgnore
    public static EnumType of(final List<EnumValue> values) {
        return new EnumType(values);
    }

    public List<EnumValue> getValues() {
        return values;
    }
}
