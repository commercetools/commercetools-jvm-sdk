package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedString;

/**
 * Updates the description of a product.
 *
 * {@include.example test.ProductCrudIntegrationTest#setDescriptionUpdateAction()}
 */
public class SetDescription extends StageableProductUpdateAction {
    private final LocalizedString description;

    private SetDescription(final LocalizedString description, final boolean staged) {
        super("setDescription", staged);
        this.description = description;
    }

    public static SetDescription of(final LocalizedString description, final boolean staged) {
        return new SetDescription(description, staged);
    }

    public static SetDescription of(final LocalizedString name) {
        return new SetDescription(name, true);
    }

    public LocalizedString getDescription() {
        return description;
    }
}
