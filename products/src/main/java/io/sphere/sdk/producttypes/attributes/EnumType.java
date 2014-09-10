package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.PlainEnumValue;

import java.util.List;

public class EnumType extends AttributeTypeBase {
    private final List<PlainEnumValue> values;

    public EnumType(final List<PlainEnumValue> values) {
        super("enum");
        this.values = values;
    }

    public List<PlainEnumValue> getValues() {
        return values;
    }
}
