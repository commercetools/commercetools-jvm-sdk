package io.sphere.sdk.producttypes.attributes;

import java.util.List;

public class EnumType extends AttributeTypeBase {
    private final List<PlainEnumValue> values;

    EnumType(final List<PlainEnumValue> values) {
        super("enum");
        this.values = values;
    }

    public List<PlainEnumValue> getValues() {
        return values;
    }
}
