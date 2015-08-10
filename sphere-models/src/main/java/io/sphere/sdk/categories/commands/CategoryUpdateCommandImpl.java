package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.commands.MetaModelUpdateCommandDslImpl;
import io.sphere.sdk.commands.MetaModelUpdateCommandDslBuilder;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Versioned;

import java.util.List;

final class CategoryUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<Category, CategoryUpdateCommand, CategoryExpansionModel<Category>> implements CategoryUpdateCommand {

    CategoryUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<Category, CategoryUpdateCommand, CategoryExpansionModel<Category>> builder) {
        super(builder);
    }

    CategoryUpdateCommandImpl(final Versioned<Category> versioned, final List<? extends UpdateAction<Category>> updateActions) {
        super(versioned, updateActions, CategoryEndpoint.ENDPOINT, CategoryUpdateCommandImpl::new, CategoryExpansionModel.of());
    }
}
