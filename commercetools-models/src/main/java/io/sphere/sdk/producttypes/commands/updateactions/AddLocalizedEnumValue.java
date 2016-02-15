package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.products.attributes.LocalizedEnumAttributeType;
import io.sphere.sdk.producttypes.ProductType;

/**
 * Adds a localized enum value to the product attribute definition.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#addLocalizedEnumValue()}
 *
 * @see LocalizedEnumAttributeType#getValues()
 */
public final class AddLocalizedEnumValue extends UpdateActionImpl<ProductType> {
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
