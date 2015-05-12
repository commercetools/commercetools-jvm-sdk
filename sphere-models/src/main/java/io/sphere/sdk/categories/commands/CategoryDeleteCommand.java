package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;

/**
 * Deletes a category in SPHERE.IO.
 *
 * {@include.example io.sphere.sdk.categories.commands.CategoryDeleteCommandTest#execution()}
 */
public final class CategoryDeleteCommand extends ByIdDeleteCommandImpl<Category> {

    private CategoryDeleteCommand(final Versioned<Category> versioned) {
        super(versioned, CategoriesEndpoint.ENDPOINT);
    }

    public static DeleteCommand<Category> of(final Versioned<Category> versioned) {
        return new CategoryDeleteCommand(versioned);
    }
}
