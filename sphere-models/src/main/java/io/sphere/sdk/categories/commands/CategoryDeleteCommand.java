package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;

/**
 * Deletes a category in SPHERE.IO.
 *
 * {@include.example io.sphere.sdk.categories.commands.CategoryDeleteCommandTest#execution()}
 */
public interface CategoryDeleteCommand extends ByIdDeleteCommand<Category> {

    static DeleteCommand<Category> of(final Versioned<Category> versioned) {
        return new CategoryDeleteCommandImpl(versioned);
    }
}
