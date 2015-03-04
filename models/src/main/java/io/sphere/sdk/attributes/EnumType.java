package io.sphere.sdk.attributes;

import io.sphere.sdk.models.PlainEnumValue;

import java.util.List;

public class EnumType extends AttributeTypeBase {
    private final List<PlainEnumValue> values;

    private EnumType(final List<PlainEnumValue> values) {
        this.values = values;
    }

    public List<PlainEnumValue> getValues() {
        return values;
    }

    public static EnumType of(final List<PlainEnumValue> values) {
        return new EnumType(values);
    }
}
