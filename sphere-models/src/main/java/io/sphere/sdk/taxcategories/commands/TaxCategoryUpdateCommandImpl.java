package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.commands.MetaModelUpdateCommandDslBuilder;
import io.sphere.sdk.commands.MetaModelUpdateCommandDslImpl;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

import java.util.List;


final class TaxCategoryUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<TaxCategory, TaxCategoryUpdateCommand, TaxCategoryExpansionModel<TaxCategory>> implements TaxCategoryUpdateCommand {
    TaxCategoryUpdateCommandImpl(final Versioned<TaxCategory> versioned, final List<? extends UpdateAction<TaxCategory>> updateActions) {
        super(versioned, updateActions, TaxCategoryEndpoint.ENDPOINT, TaxCategoryUpdateCommandImpl::new, TaxCategoryExpansionModel.of());
    }

    TaxCategoryUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<TaxCategory, TaxCategoryUpdateCommand, TaxCategoryExpansionModel<TaxCategory>> builder) {
        super(builder);
    }
}
