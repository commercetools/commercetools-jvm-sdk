package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.commands.Command;
import io.sphere.sdk.commands.CreateCommandImpl;

/**
 * Command to create a category.
 *
 *
 * For construction of a {@link CategoryDraft} (a draft for a new category) use a {@link io.sphere.sdk.categories.CategoryDraftBuilder}:
 *
 *
 * {@include.example io.sphere.sdk.categories.commands.CategoryCreateCommandTest#execution()}
 */
public final class CategoryCreateCommandImpl extends CreateCommandImpl<Category, CategoryDraft> implements Command<Category> {

    private CategoryCreateCommandImpl(final CategoryDraft categoryDraft) {
        super(categoryDraft, CategoryEndpoint.ENDPOINT);
    }

    public static CategoryCreateCommandImpl of(final CategoryDraft categoryDraft) {
        return new CategoryCreateCommandImpl(categoryDraft);
    }
}
