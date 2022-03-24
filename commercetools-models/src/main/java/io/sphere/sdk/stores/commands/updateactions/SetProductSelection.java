package io.sphere.sdk.stores.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;

import java.util.List;
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
public final class SetProductSelection extends UpdateActionImpl<Store> {
    private final List<ProductSelectionSettingDraft> productSelections;

    private SetProductSelection(final List<ProductSelectionSettingDraft> productSelections) {
        super("setProductSelection");
        this.productSelections = productSelections;
    }

    public List<ProductSelectionSettingDraft> getProductSelection() {
        return productSelections;
    }

    public static SetProductSelection of(final List<ProductSelectionSettingDraft> productSelections) {
        return new SetProductSelection(productSelections);
    }
}
