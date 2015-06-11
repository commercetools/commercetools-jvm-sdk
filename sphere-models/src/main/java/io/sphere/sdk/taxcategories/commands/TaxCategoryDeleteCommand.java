package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.taxcategories.TaxCategory;

/** Deletes a tax category.

 <p>Example:</p>
 {@include.example io.sphere.sdk.taxcategories.TaxCategoryIntegrationTest#demoForDeletion()}

 */
public class TaxCategoryDeleteCommand extends ByIdDeleteCommandImpl<TaxCategory> {
    private TaxCategoryDeleteCommand(final Versioned<TaxCategory> versioned) {
        super(versioned, TaxCategoryEndpoint.ENDPOINT);
    }

    public static DeleteCommand<TaxCategory> of(final Versioned<TaxCategory> versioned) {
        return new TaxCategoryDeleteCommand(versioned);
    }
}
