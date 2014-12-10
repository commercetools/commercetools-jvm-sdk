package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.DeleteByIdCommandImpl;
import io.sphere.sdk.models.Versioned;

/**
 * Deletes a category in SPHERE.IO.
 *
 *
 */
public final class CategoryDeleteByIdCommand extends DeleteByIdCommandImpl<Category> {

    private CategoryDeleteByIdCommand(final Versioned<Category> versioned) {
        super(versioned, CategoriesEndpoint.ENDPOINT);
    }

    public static CategoryDeleteByIdCommand of(final Versioned<Category> versioned) {
        return new CategoryDeleteByIdCommand(versioned);
    }
}
