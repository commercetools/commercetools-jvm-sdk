package io.sphere.sdk.producttypes.attributes;

import java.util.List;

public class LocalizedEnumType extends AttributeTypeBase {
    private final List<LocalizedEnumValue> values;

    public LocalizedEnumType(final List<LocalizedEnumValue> values) {
        super("lenum");
        this.values = values;
    }

    public List<LocalizedEnumValue> getValues() {
        return values;
    }
}
