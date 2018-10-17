package io.sphere.sdk.shippingmethods.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.taxcategories.TaxCategory;

/**
 *
 * Changes the tax category.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommandIntegrationTest#changeTaxCategory()}
 */
public final class ChangeTaxCategory extends UpdateActionImpl<ShippingMethod> {
    private final ResourceIdentifier<TaxCategory> taxCategory;

    private ChangeTaxCategory(final ResourceIdentifier<TaxCategory> taxCategory) {
        super("changeTaxCategory");
        this.taxCategory = taxCategory;
    }

    public ResourceIdentifier<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    public static ChangeTaxCategory of(final Referenceable<TaxCategory> taxCategory) {
        return new ChangeTaxCategory(taxCategory.toReference());
    }

    public static ChangeTaxCategory of(final ResourceIdentifier<TaxCategory> taxCategory) {
        return new ChangeTaxCategory(taxCategory);
    }
}
