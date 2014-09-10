package io.sphere.sdk.taxcategories.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.commands.DeleteByIdCommandImpl;
import io.sphere.sdk.taxcategories.TaxCategory;

public class TaxCategoryDeleteByIdCommand extends DeleteByIdCommandImpl<TaxCategory> {
    public TaxCategoryDeleteByIdCommand(final Versioned<TaxCategory> versioned) {
        super(versioned);
    }

    @Override
    protected String baseEndpointWithoutId() {
        return "/tax-categories";
    }

    @Override
    protected TypeReference<TaxCategory> typeReference() {
        return TaxCategory.typeReference();
    }
}
