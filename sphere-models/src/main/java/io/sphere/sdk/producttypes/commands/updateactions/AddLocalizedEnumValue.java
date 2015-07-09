package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.producttypes.ProductType;

/**
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandTest#addLocalizedEnumValue()}
 */
public class AddLocalizedEnumValue extends UpdateAction<ProductType> {
    private final String attributeName;
    private final LocalizedEnumValue value;

    private AddLocalizedEnumValue(final String attributeName, final LocalizedEnumValue value) {
        super("addLocalizedEnumValue");
        this.attributeName = attributeName;
        this.value = value;
    }

    public static AddLocalizedEnumValue of(final String attributeName, final LocalizedEnumValue value) {
        return new AddLocalizedEnumValue(attributeName, value);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public LocalizedEnumValue getValue() {
        return value;
    }
}
