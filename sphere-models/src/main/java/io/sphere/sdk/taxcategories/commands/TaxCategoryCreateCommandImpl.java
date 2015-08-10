package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandBuilder;
import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandImpl;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryDraft;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

final class TaxCategoryCreateCommandImpl extends ReferenceExpandeableCreateCommandImpl<TaxCategory, TaxCategoryCreateCommand, TaxCategoryDraft, TaxCategoryExpansionModel<TaxCategory>> implements TaxCategoryCreateCommand {
    TaxCategoryCreateCommandImpl(final ReferenceExpandeableCreateCommandBuilder<TaxCategory, TaxCategoryCreateCommand, TaxCategoryDraft, TaxCategoryExpansionModel<TaxCategory>> builder) {
        super(builder);
    }

    TaxCategoryCreateCommandImpl(final TaxCategoryDraft body) {
        super(body, TaxCategoryEndpoint.ENDPOINT, TaxCategoryExpansionModel.of(), TaxCategoryCreateCommandImpl::new);
    }
}
