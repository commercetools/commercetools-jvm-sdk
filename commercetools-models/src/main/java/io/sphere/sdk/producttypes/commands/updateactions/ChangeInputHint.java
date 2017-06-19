package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.TextInputHint;
import io.sphere.sdk.producttypes.ProductType;

/**
 * Change Attribute Definition InputHint.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#changeInputHint()}
 */
public final class ChangeInputHint extends UpdateActionImpl<ProductType> {

    private final String attributeName;
    private final TextInputHint newValue;

    protected ChangeInputHint(final String attributeName, final TextInputHint newValue) {
        super("changeInputHint");
        this.attributeName = attributeName;
        this.newValue = newValue;
    }

    public static ChangeInputHint of(final String attributeName, final TextInputHint newValue) {
        return new ChangeInputHint(attributeName, newValue);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public TextInputHint getNewValue() {
        return newValue;
    }
}
