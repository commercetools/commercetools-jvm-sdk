package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.producttypes.ProductType;

import javax.annotation.Nullable;

/**
 * Sets the attribute input tip. Allows to set additional information about the specified attribute.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#setInputTip()}
 *
 * @see AttributeDefinition#getInputTip()
 */
public final class SetInputTip extends UpdateActionImpl<ProductType> {

    private final String attributeName;
    @Nullable
    private final LocalizedString inputTip;

    protected SetInputTip(final String attributeName, @Nullable final LocalizedString inputTip) {
        super("setInputTip");
        this.attributeName = attributeName;
        this.inputTip = inputTip;
    }

    public static SetInputTip of(final String attributeName, @Nullable final LocalizedString inputTip) {
        return new SetInputTip(attributeName, inputTip);
    }

    public String getAttributeName() {
        return attributeName;
    }

    @Nullable
    public LocalizedString getInputTip() {
        return inputTip;
    }
}
