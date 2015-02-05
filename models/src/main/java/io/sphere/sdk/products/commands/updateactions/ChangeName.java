package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.ProductUpdateScope;

/**
 * Updates the name of a product.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#changeName()}
 */
public class ChangeName extends StageableProductUpdateAction {
    private final LocalizedStrings name;

    private ChangeName(final LocalizedStrings name, final ProductUpdateScope productUpdateScope) {
        super("changeName", productUpdateScope);
        this.name = name;
    }

    public static ChangeName of(final LocalizedStrings name, final ProductUpdateScope productUpdateScope) {
        return new ChangeName(name, productUpdateScope);
    }

    public LocalizedStrings getName() {
        return name;
    }
}
