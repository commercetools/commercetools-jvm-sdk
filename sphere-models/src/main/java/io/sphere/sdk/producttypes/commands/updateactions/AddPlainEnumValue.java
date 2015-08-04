package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.producttypes.ProductType;

/**
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandTest#addPlainEnumValue()}
 */
public class AddPlainEnumValue extends UpdateActionImpl<ProductType> {
    private final String attributeName;
    private final EnumValue value;

    private AddPlainEnumValue(final String attributeName, final EnumValue value) {
        super("addPlainEnumValue");
        this.attributeName = attributeName;
        this.value = value;
    }

    public static AddPlainEnumValue of(final String attributeName, final EnumValue value) {
        return new AddPlainEnumValue(attributeName, value);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public EnumValue getValue() {
        return value;
    }
}
