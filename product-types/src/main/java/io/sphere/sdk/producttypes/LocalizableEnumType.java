package io.sphere.sdk.producttypes;

import java.util.List;

public class LocalizableEnumType implements AttributeType {
    final List<LocalizedEnumValue> values;

    LocalizableEnumType(List<LocalizedEnumValue> values) {
        this.values = values;
    }

    @Override
    public String getName() {
        return "lenum";
    }

    public List<LocalizedEnumValue> getValues() {
        return values;
    }
}
