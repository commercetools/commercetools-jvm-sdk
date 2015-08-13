package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

/** Deletes a tax category.

 <p>Example:</p>
 {@include.example io.sphere.sdk.taxcategories.TaxCategoryIntegrationTest#demoForDeletion()}

 */
public interface TaxCategoryDeleteCommand extends ByIdDeleteCommand<TaxCategory>, MetaModelExpansionDsl<TaxCategory, TaxCategoryDeleteCommand, TaxCategoryExpansionModel<TaxCategory>> {
    static TaxCategoryDeleteCommand of(final Versioned<TaxCategory> versioned) {
        return new TaxCategoryDeleteCommandImpl(versioned);
    }
}
