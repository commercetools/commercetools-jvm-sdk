package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;

/**
 * Command to create a category.
 *
 *
 * For construction of a {@link io.sphere.sdk.categories.CategoryDraft} (a draft for a new category) use a {@link io.sphere.sdk.categories.CategoryDraftBuilder}:
 *
 *
 * {@include.example io.sphere.sdk.categories.commands.CategoryCreateCommandIntegrationTest#execution()}
 */
public interface CategoryCreateCommand extends DraftBasedCreateCommand<Category, CategoryDraft>, MetaModelReferenceExpansionDsl<Category, CategoryCreateCommand, CategoryExpansionModel<Category>> {

    static CategoryCreateCommand of(final CategoryDraft categoryDraft) {
        return new CategoryCreateCommandImpl(categoryDraft);
    }
}
