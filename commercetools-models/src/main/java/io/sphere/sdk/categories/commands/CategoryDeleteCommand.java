package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;

/**
 * Deletes a category.
 *
 * {@include.example io.sphere.sdk.categories.commands.CategoryDeleteCommandIntegrationTest#execution()}
 */
public interface CategoryDeleteCommand extends MetaModelReferenceExpansionDsl<Category, CategoryDeleteCommand, CategoryExpansionModel<Category>>, DeleteCommand<Category> {

    static CategoryDeleteCommand of(final Versioned<Category> versioned) {
        return new CategoryDeleteCommandImpl(versioned);
    }
}
