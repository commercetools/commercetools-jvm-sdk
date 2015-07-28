package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslBuilder;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;

import java.util.List;

final class CategoryUpdateCommandImpl extends UpdateCommandDslImpl<Category, CategoryUpdateCommand> implements CategoryUpdateCommand {
    CategoryUpdateCommandImpl(final Versioned<Category> versioned, final List<? extends UpdateAction<Category>> updateActions) {
        super(versioned, updateActions, CategoryEndpoint.ENDPOINT, CategoryUpdateCommandImpl::new);
    }

    CategoryUpdateCommandImpl(final UpdateCommandDslBuilder<Category, CategoryUpdateCommand> builder) {
        super(builder);
    }
}
