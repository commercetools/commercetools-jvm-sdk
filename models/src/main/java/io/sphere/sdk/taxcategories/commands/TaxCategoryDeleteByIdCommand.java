package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.commands.DeleteByIdCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.taxcategories.TaxCategory;

/** Deletes a tax category.

 <p>Example:</p>
 {@include.example io.sphere.sdk.taxcategories.TaxCategoryIntegrationTest#demoForDeletion()}

 */
public class TaxCategoryDeleteByIdCommand extends DeleteByIdCommandImpl<TaxCategory> {
    private TaxCategoryDeleteByIdCommand(final Versioned<TaxCategory> versioned) {
        super(versioned, TaxCategoriesEndpoint.ENDPOINT);
    }

    public static TaxCategoryDeleteByIdCommand of(final Versioned<TaxCategory> versioned) {
        return new TaxCategoryDeleteByIdCommand(versioned);
    }
}
