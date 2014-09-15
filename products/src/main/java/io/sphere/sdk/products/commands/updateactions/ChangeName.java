package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedString;

/**
 * Updates the name of a product.
 *
 * {@include.example test.ProductCrudIntegrationTest#changeNameUpdateAction()}
 */
public class ChangeName extends StageableProductUpdateAction {
    private final LocalizedString name;

    private ChangeName(final LocalizedString name, final boolean staged) {
        super("changeName", staged);
        this.name = name;
    }

    public static ChangeName of(final LocalizedString name, final boolean staged) {
        return new ChangeName(name, staged);
    }

    public static ChangeName of(final LocalizedString name) {
        return new ChangeName(name, true);
    }

    public LocalizedString getName() {
        return name;
    }
}
