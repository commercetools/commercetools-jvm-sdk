package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.taxcategories.TaxCategoryDraft;
import io.sphere.sdk.taxcategories.TaxCategory;

/** Creates a tax category.

 {@include.example io.sphere.sdk.taxcategories.TaxCategoryIntegrationTest#createTaxCategory()}

 @see io.sphere.sdk.taxcategories.TaxCategoryDraft
 */
public class TaxCategoryCreateCommand extends CreateCommandImpl<TaxCategory, TaxCategoryDraft> {
    private TaxCategoryCreateCommand(final TaxCategoryDraft body) {
        super(body, TaxCategoryEndpoint.ENDPOINT);
    }

    public static TaxCategoryCreateCommand of(final TaxCategoryDraft body) {
        return new TaxCategoryCreateCommand(body);
    }
}
