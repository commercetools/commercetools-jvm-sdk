package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.PlainEnumValue;
import io.sphere.sdk.producttypes.ProductType;

/**
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandTest#addPlainEnumValue()}
 */
public class AddPlainEnumValue extends UpdateActionImpl<ProductType> {
    private final String attributeName;
    private final PlainEnumValue value;

    private AddPlainEnumValue(final String attributeName, final PlainEnumValue value) {
        super("addPlainEnumValue");
        this.attributeName = attributeName;
        this.value = value;
    }

    public static AddPlainEnumValue of(final String attributeName, final PlainEnumValue value) {
        return new AddPlainEnumValue(attributeName, value);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public PlainEnumValue getValue() {
        return value;
    }
}
