package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.producttypes.ProductType;

import java.util.List;

/**
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandTest#changePlainEnumValueOrder()}
 */
public class ChangePlainEnumValueOrder extends UpdateActionImpl<ProductType> {
    private final String attributeName;
    private final List<EnumValue> values;

    private ChangePlainEnumValueOrder(final String attributeName, final List<EnumValue> values) {
        super("changePlainEnumValueOrder");
        this.attributeName = attributeName;
        this.values = values;
    }

    public static ChangePlainEnumValueOrder of(final String attributeName, final List<EnumValue> values) {
        return new ChangePlainEnumValueOrder(attributeName, values);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public List<EnumValue> getValues() {
        return values;
    }
}
