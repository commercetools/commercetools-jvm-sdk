package io.sphere.sdk.stores.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.stores.Store;

/**
 * Remove a Product Selection.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.stores.commands.StoreUpdateCommandIntegrationTest}
 *
 * @see Store#getProductSelections() ()
 */
public final class RemoveProductSelection extends UpdateActionImpl<Store> {
    private final ResourceIdentifier<ProductSelection> productSelection;

    private RemoveProductSelection(final ResourceIdentifier<ProductSelection> productSelection) {
        super("removeProductSelection");
        this.productSelection = productSelection;
    }

    public ResourceIdentifier<ProductSelection> getProductSelection() {
        return productSelection;
    }

    public static RemoveProductSelection of(final ResourceIdentifier<ProductSelection> productSelection) {
        return new RemoveProductSelection(productSelection);
    }
}
