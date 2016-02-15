package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.products.attributes.EnumAttributeType;
import io.sphere.sdk.producttypes.ProductType;

/**
 * Adds an enum value to an enum attribute definition.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#addEnumValue()}
 *
 * @see EnumAttributeType#getValues()
 */
public final class AddEnumValue extends UpdateActionImpl<ProductType> {
    private final String attributeName;
    private final EnumValue value;

    private AddEnumValue(final String attributeName, final EnumValue value) {
        super("addPlainEnumValue");
        this.attributeName = attributeName;
        this.value = value;
    }

    public static AddEnumValue of(final String attributeName, final EnumValue value) {
        return new AddEnumValue(attributeName, value);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public EnumValue getValue() {
        return value;
    }
}
