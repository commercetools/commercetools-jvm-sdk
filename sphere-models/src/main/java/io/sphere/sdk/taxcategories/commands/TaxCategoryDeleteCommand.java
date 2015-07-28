package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.taxcategories.TaxCategory;

/** Deletes a tax category.

 <p>Example:</p>
 {@include.example io.sphere.sdk.taxcategories.TaxCategoryIntegrationTest#demoForDeletion()}

 */
public interface TaxCategoryDeleteCommand extends ByIdDeleteCommand<TaxCategory> {
    static DeleteCommand<TaxCategory> of(final Versioned<TaxCategory> versioned) {
        return new TaxCategoryDeleteCommandImpl(versioned);
    }
}
