package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryDraft;

final class TaxCategoryCreateCommandImpl extends CreateCommandImpl<TaxCategory, TaxCategoryDraft> implements TaxCategoryCreateCommand {
    TaxCategoryCreateCommandImpl(final TaxCategoryDraft body) {
        super(body, TaxCategoryEndpoint.ENDPOINT);
    }
}
