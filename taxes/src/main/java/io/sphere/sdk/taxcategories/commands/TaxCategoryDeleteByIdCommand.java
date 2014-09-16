package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.commands.DeleteByIdCommandImpl;
import io.sphere.sdk.taxcategories.TaxCategory;

public class TaxCategoryDeleteByIdCommand extends DeleteByIdCommandImpl<TaxCategory> {
    public TaxCategoryDeleteByIdCommand(final Versioned<TaxCategory> versioned) {
        super(versioned, TaxCategoriesEndpoint.ENDPOINT);
    }
}
