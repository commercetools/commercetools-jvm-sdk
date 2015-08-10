package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

final class TaxCategoryDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<TaxCategory, TaxCategoryDeleteCommand, TaxCategoryExpansionModel<TaxCategory>> implements TaxCategoryDeleteCommand {
    TaxCategoryDeleteCommandImpl(final Versioned<TaxCategory> versioned) {
        super(versioned, TaxCategoryEndpoint.ENDPOINT, TaxCategoryExpansionModel.of(), TaxCategoryDeleteCommandImpl::new);
    }


    TaxCategoryDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<TaxCategory, TaxCategoryDeleteCommand, TaxCategoryExpansionModel<TaxCategory>> builder) {
        super(builder);
    }
}
