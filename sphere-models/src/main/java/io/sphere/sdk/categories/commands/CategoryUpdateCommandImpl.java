package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.commands.*;
import io.sphere.sdk.models.Versioned;

import java.util.List;

final class CategoryUpdateCommandImpl extends ReferenceExpandeableUpdateCommandDslImpl<Category, CategoryUpdateCommand, CategoryExpansionModel<Category>> implements CategoryUpdateCommand {
    CategoryUpdateCommandImpl(final Versioned<Category> versioned, final List<? extends UpdateAction<Category>> updateActions) {
        super(versioned, updateActions, CategoryEndpoint.ENDPOINT, CategoryUpdateCommandImpl::new, CategoryExpansionModel.of());
    }

    CategoryUpdateCommandImpl(final ReferenceExpanseableUpdateCommandDslBuilder<Category, CategoryUpdateCommand, CategoryExpansionModel<Category>> builder) {
        super(builder);
    }
}
