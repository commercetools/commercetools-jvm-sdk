package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Updates the description of a product.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setDescription()}
 */
public final class SetDescription extends UpdateActionImpl<Product> {
    private final LocalizedString description;
    @Nullable
    private final boolean staged;

    private SetDescription(final LocalizedString description, final boolean staged) {
        super("setDescription");
        this.description = description;
        this.staged = staged;
    }

    public static SetDescription of(final LocalizedString description) {
        return of(description, true);
    }

    public static SetDescription of(final LocalizedString description, @Nullable final boolean staged) {
        return new SetDescription(description, staged);
    }

    public LocalizedString getDescription() {
        return description;
    }

    @Nullable
    public boolean isStaged() {
        return staged;
    }
}
