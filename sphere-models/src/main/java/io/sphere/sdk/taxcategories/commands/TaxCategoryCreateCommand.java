package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryDraft;

/** Creates a tax category.

 {@include.example io.sphere.sdk.taxcategories.TaxCategoryIntegrationTest#createTaxCategory()}

 @see io.sphere.sdk.taxcategories.TaxCategoryDraft
 */
public interface TaxCategoryCreateCommand extends CreateCommand<TaxCategory> {
    static TaxCategoryCreateCommand of(final TaxCategoryDraft body) {
        return new TaxCategoryCreateCommandImpl(body);
    }
}
