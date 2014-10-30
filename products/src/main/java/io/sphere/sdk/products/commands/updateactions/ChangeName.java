package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedStrings;

/**
 * Updates the name of a product.
 *
 * {@include.example io.sphere.sdk.products.ProductCrudIntegrationTest#changeNameUpdateAction()}
 */
public class ChangeName extends StageableProductUpdateAction {
    private final LocalizedStrings name;

    private ChangeName(final LocalizedStrings name, final boolean staged) {
        super("changeName", staged);
        this.name = name;
    }

    public static ChangeName of(final LocalizedStrings name, final boolean staged) {
        return new ChangeName(name, staged);
    }

    public static ChangeName of(final LocalizedStrings name) {
        return new ChangeName(name, true);
    }

    public LocalizedStrings getName() {
        return name;
    }
}
