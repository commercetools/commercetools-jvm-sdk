package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.taxcategories.NewTaxCategory;
import io.sphere.sdk.taxcategories.TaxCategory;

/** Creates a tax category.

 {@include.example io.sphere.sdk.taxcategories.TaxCategoryIntegrationTest#createTaxCategory()}

 @see io.sphere.sdk.taxcategories.NewTaxCategory
 */
public class TaxCategoryCreateCommand extends CreateCommandImpl<TaxCategory, NewTaxCategory> {
    public TaxCategoryCreateCommand(final NewTaxCategory body) {
        super(body, TaxCategoriesEndpoint.ENDPOINT);
    }
}
