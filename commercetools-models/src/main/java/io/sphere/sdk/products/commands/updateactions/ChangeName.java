package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Updates the name of a product.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#changeName()}
 */
public final class ChangeName extends UpdateActionImpl<Product> {
    private final LocalizedString name;
    @Nullable
    private final boolean staged;

    private ChangeName(final LocalizedString name, final boolean staged) {
        super("changeName");
        this.name = name;
        this.staged = staged;
    }

    public static ChangeName of(final LocalizedString name) {
        return of(name, true);
    }

    public static ChangeName of(final LocalizedString name, @Nullable final boolean staged) {
        return new ChangeName(name, staged);
    }

    public LocalizedString getName() {
        return name;
    }

    @Nullable
    public boolean isStaged() {
        return staged;
    }
}
