package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedStrings;

/**
 * Updates the description of a product.
 *
 * {@include.example io.sphere.sdk.products.ProductCrudIntegrationTest#setDescriptionUpdateAction()}
 */
public class SetDescription extends StageableProductUpdateAction {
    private final LocalizedStrings description;

    private SetDescription(final LocalizedStrings description, final boolean staged) {
        super("setDescription", staged);
        this.description = description;
    }

    public static SetDescription of(final LocalizedStrings description, final boolean staged) {
        return new SetDescription(description, staged);
    }

    public static SetDescription of(final LocalizedStrings name) {
        return new SetDescription(name, true);
    }

    public LocalizedStrings getDescription() {
        return description;
    }
}
