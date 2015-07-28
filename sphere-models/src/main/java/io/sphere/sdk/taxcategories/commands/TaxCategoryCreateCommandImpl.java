package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryDraft;

/** Creates a tax category.

 {@include.example io.sphere.sdk.taxcategories.TaxCategoryIntegrationTest#createTaxCategory()}

 @see TaxCategoryDraft
 */
public class TaxCategoryCreateCommandImpl extends CreateCommandImpl<TaxCategory, TaxCategoryDraft> {
    private TaxCategoryCreateCommandImpl(final TaxCategoryDraft body) {
        super(body, TaxCategoryEndpoint.ENDPOINT);
    }

    public static TaxCategoryCreateCommandImpl of(final TaxCategoryDraft body) {
        return new TaxCategoryCreateCommandImpl(body);
    }
}
