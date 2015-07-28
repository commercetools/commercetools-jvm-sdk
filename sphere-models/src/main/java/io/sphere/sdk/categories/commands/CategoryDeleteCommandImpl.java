package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;

final class CategoryDeleteCommandImpl extends ByIdDeleteCommandImpl<Category> implements CategoryDeleteCommand {

    CategoryDeleteCommandImpl(final Versioned<Category> versioned) {
        super(versioned, CategoryEndpoint.ENDPOINT);
    }
}
