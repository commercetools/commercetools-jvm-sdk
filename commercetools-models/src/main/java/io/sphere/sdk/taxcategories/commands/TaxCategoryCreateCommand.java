package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryDraft;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

/** Creates a tax category.

 {@include.example io.sphere.sdk.taxcategories.TaxCategoryIntegrationTest#createTaxCategory()}

 @see io.sphere.sdk.taxcategories.TaxCategoryDraft
 */
public interface TaxCategoryCreateCommand extends DraftBasedCreateCommand<TaxCategory, TaxCategoryDraft>, MetaModelReferenceExpansionDsl<TaxCategory, TaxCategoryCreateCommand, TaxCategoryExpansionModel<TaxCategory>> {
    static TaxCategoryCreateCommand of(final TaxCategoryDraft body) {
        return new TaxCategoryCreateCommandImpl(body);
    }
}
