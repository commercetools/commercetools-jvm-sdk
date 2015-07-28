package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.taxcategories.TaxCategory;

final class TaxCategoryDeleteCommandImpl extends ByIdDeleteCommandImpl<TaxCategory> {
    TaxCategoryDeleteCommandImpl(final Versioned<TaxCategory> versioned) {
        super(versioned, TaxCategoryEndpoint.ENDPOINT);
    }
}
