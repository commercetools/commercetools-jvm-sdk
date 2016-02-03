package io.sphere.sdk.taxcategories.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import static java.util.Objects.requireNonNull;

/**
 * Adds a tax rate.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.taxcategories.commands.TaxCategoryUpdateCommandTest#addTaxRate()}
 *
 * @see TaxCategory#getTaxRates()
 * @see RemoveTaxRate
 * @see ReplaceTaxRate
 */
public final class AddTaxRate extends UpdateActionImpl<TaxCategory> {
    private final TaxRate taxRate;

    private AddTaxRate(final TaxRate taxRate) {
        super("addTaxRate");
        this.taxRate = requireNonNull(taxRate);
    }

    public static AddTaxRate of(final TaxRate taxRate) {
        return new AddTaxRate(taxRate);
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }
}
