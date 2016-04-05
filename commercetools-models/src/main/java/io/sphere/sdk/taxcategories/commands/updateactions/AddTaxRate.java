package io.sphere.sdk.taxcategories.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRateDraft;

import static java.util.Objects.requireNonNull;

/**
 * Adds a tax rate.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.taxcategories.commands.TaxCategoryUpdateCommandIntegrationTest#addTaxRate()}
 *
 * @see TaxCategory#getTaxRates()
 * @see RemoveTaxRate
 * @see ReplaceTaxRate
 */
public final class AddTaxRate extends UpdateActionImpl<TaxCategory> {
    private final TaxRateDraft taxRate;

    private AddTaxRate(final TaxRateDraft taxRate) {
        super("addTaxRate");
        this.taxRate = requireNonNull(taxRate);
    }

    public static AddTaxRate of(final TaxRateDraft taxRate) {
        return new AddTaxRate(taxRate);
    }

    public TaxRateDraft getTaxRate() {
        return taxRate;
    }
}
