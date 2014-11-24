package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.commands.Command;
import io.sphere.sdk.commands.CreateCommandImpl;

/**
 * Command to create a category.
 *
 *
 * For construction of a {@link io.sphere.sdk.categories.CategoryDraft} (a draft for a new category) use a {@link io.sphere.sdk.categories.CategoryDraftBuilder}:
 *
 */
public final class CategoryCreateCommand extends CreateCommandImpl<Category, CategoryDraft> implements Command<Category> {

    public CategoryCreateCommand(final CategoryDraft categoryDraft) {
        super(categoryDraft, CategoriesEndpoint.ENDPOINT);
    }
}
