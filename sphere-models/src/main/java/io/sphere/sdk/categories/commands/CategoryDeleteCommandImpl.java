package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;

final class CategoryDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<Category, CategoryDeleteCommand, CategoryExpansionModel<Category>> implements CategoryDeleteCommand {

    CategoryDeleteCommandImpl(final Versioned<Category> versioned) {
        super(versioned, CategoryEndpoint.ENDPOINT, CategoryExpansionModel.of(), CategoryDeleteCommandImpl::new);
    }

    CategoryDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<Category, CategoryDeleteCommand, CategoryExpansionModel<Category>> builder) {
        super(builder);
    }
}
