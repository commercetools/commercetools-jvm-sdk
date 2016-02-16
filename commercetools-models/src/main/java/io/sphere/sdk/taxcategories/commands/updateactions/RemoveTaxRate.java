package io.sphere.sdk.taxcategories.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.taxcategories.TaxCategory;

import static java.util.Objects.requireNonNull;

/**
 * Removes a tax rate.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.taxcategories.commands.TaxCategoryUpdateCommandIntegrationTest#addTaxRate()}
 */
public final class RemoveTaxRate extends UpdateActionImpl<TaxCategory> {
    private final String taxRateId;

    private RemoveTaxRate(final String taxRateId) {
        super("removeTaxRate");
        this.taxRateId = requireNonNull(taxRateId);
    }

    public static RemoveTaxRate of(final String taxRateId) {
        return new RemoveTaxRate(taxRateId);
    }

    public String getTaxRateId() {
        return taxRateId;
    }
}
