package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.requests.DeleteCommandImpl;

public class TaxCategoryDeleteCommand extends DeleteCommandImpl<TaxCategory> {
    public TaxCategoryDeleteCommand(final Versioned versionData) {
        super(versionData);
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
