package io.sphere.sdk.producttypes;

import java.util.List;

public class EnumType implements AttributeType {

    final List<PlainEnumValue> values;

    EnumType(List<PlainEnumValue> values) {
        this.values = values;
    }

    @Override
    public String getName() {
        return "enum";
    }

    public List<PlainEnumValue> getValues() {
        return values;
    }
}
