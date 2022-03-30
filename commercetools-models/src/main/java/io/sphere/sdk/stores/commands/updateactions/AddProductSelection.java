package io.sphere.sdk.stores.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.stores.ProductSelectionSettingDraft;
import io.sphere.sdk.stores.Store;

/**
 * Add a Product Selection.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.stores.commands.StoreUpdateCommandIntegrationTest}
 *
 * @see Store#getProductSelections() ()
 */
public final class AddProductSelection extends UpdateActionImpl<Store> {
    private final ProductSelectionSettingDraft productSelection;

    private AddProductSelection(final ProductSelectionSettingDraft productSelection) {
        super("addProductSelection");
        this.productSelection = productSelection;
    }

    public ProductSelectionSettingDraft getProductSelection() {
        return productSelection;
    }

    public static AddProductSelection of(final ProductSelectionSettingDraft productSelection) {
        return new AddProductSelection(productSelection);
    }
}
