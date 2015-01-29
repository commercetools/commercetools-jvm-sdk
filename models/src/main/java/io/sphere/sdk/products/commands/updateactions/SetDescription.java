package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.ProductUpdateScope;

/**
 * Updates the description of a product.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setDescription()}
 */
public class SetDescription extends StageableProductUpdateAction {
    private final LocalizedStrings description;

    private SetDescription(final LocalizedStrings description, final ProductUpdateScope productUpdateScope) {
        super("setDescription", productUpdateScope);
        this.description = description;
    }

    public static SetDescription of(final LocalizedStrings description, final ProductUpdateScope productUpdateScope) {
        return new SetDescription(description, productUpdateScope);
    }

    public LocalizedStrings getDescription() {
        return description;
    }
}
