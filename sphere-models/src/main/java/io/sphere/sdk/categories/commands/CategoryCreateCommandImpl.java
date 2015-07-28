package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.commands.CreateCommandImpl;

final class CategoryCreateCommandImpl extends CreateCommandImpl<Category, CategoryDraft> implements CategoryCreateCommand {

    CategoryCreateCommandImpl(final CategoryDraft categoryDraft) {
        super(categoryDraft, CategoryEndpoint.ENDPOINT);
    }
}
