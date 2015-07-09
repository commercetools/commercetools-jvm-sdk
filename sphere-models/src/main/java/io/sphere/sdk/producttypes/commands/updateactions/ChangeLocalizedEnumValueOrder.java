package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.producttypes.ProductType;

import java.util.List;

/**
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandTest#changeLocalizedEnumValueOrder()}
 */
public class ChangeLocalizedEnumValueOrder extends UpdateAction<ProductType> {
    private final String attributeName;
    private final List<LocalizedEnumValue> values;

    private ChangeLocalizedEnumValueOrder(final String attributeName, final List<LocalizedEnumValue> values) {
        super("changeLocalizedEnumValueOrder");
        this.attributeName = attributeName;
        this.values = values;
    }

    public static ChangeLocalizedEnumValueOrder of(final String attributeName, final List<LocalizedEnumValue> values) {
        return new ChangeLocalizedEnumValueOrder(attributeName, values);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public List<LocalizedEnumValue> getValues() {
        return values;
    }
}
