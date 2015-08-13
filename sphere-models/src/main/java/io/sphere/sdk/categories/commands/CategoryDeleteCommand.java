package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;

/**
 * Deletes a category in SPHERE.IO.
 *
 * {@include.example io.sphere.sdk.categories.commands.CategoryDeleteCommandTest#execution()}
 */
public interface CategoryDeleteCommand extends ByIdDeleteCommand<Category>, MetaModelExpansionDsl<Category, CategoryDeleteCommand, CategoryExpansionModel<Category>> {

    static CategoryDeleteCommand of(final Versioned<Category> versioned) {
        return new CategoryDeleteCommandImpl(versioned);
    }
}
