package io.sphere.sdk.stores.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.stores.Store;

import javax.annotation.Nullable;

/**
 * Change a Product Selection.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.stores.commands.StoreUpdateCommandIntegrationTest}
 *
 * @see Store#getProductSelections() ()
 */
public final class ChangeProductSelection extends UpdateActionImpl<Store> {
    private final ResourceIdentifier<ProductSelection> productSelection;
    @Nullable
    private final Boolean active;

    private ChangeProductSelection(final ResourceIdentifier<ProductSelection> productSelection, @Nullable final Boolean active) {
        super("changeProductSelectionActive");
        this.productSelection = productSelection;
        this.active = active;
    }

    public ResourceIdentifier<ProductSelection> getProductSelection() {
        return productSelection;
    }

    @Nullable
    public Boolean getActive() {
        return active;
    }

    public static ChangeProductSelection of(final ResourceIdentifier<ProductSelection> productSelection, @Nullable final Boolean active) {
        return new ChangeProductSelection(productSelection, active);
    }
}
