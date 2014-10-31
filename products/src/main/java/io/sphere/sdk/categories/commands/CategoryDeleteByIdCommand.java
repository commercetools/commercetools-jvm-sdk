package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.DeleteByIdCommandImpl;
import io.sphere.sdk.models.Versioned;

/**
 * Deletes a category in SPHERE.IO.
 *
 * <p>Example:</p>
 *
 */
public final class CategoryDeleteByIdCommand extends DeleteByIdCommandImpl<Category> {

    public CategoryDeleteByIdCommand(final Versioned<Category> versioned) {
        super(versioned, CategoriesEndpoint.ENDPOINT);
    }
}
