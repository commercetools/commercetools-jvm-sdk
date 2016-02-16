package io.sphere.sdk.taxcategories.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import static java.util.Objects.requireNonNull;

/**
 * Replaces a tax rate.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.taxcategories.commands.TaxCategoryUpdateCommandIntegrationTest#replaceTaxRate()}
 */
public final class ReplaceTaxRate extends UpdateActionImpl<TaxCategory> {
    private final TaxRate taxRate;
    private final String taxRateId;

    private ReplaceTaxRate(final String taxRateId, final TaxRate taxRate) {
        super("replaceTaxRate");
        this.taxRateId = taxRateId;
        this.taxRate = requireNonNull(taxRate);
    }

    public static ReplaceTaxRate of(final String taxRateId, final TaxRate taxRate) {
        return new ReplaceTaxRate(taxRateId, taxRate);
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }

    public String getTaxRateId() {
        return taxRateId;
    }
}
