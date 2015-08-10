package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandBuilder;
import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandImpl;

final class CategoryCreateCommandImpl extends ReferenceExpandeableCreateCommandImpl<Category, CategoryCreateCommand, CategoryDraft, CategoryExpansionModel<Category>> implements CategoryCreateCommand {

    CategoryCreateCommandImpl(final CategoryDraft categoryDraft) {
        super(categoryDraft, CategoryEndpoint.ENDPOINT, CategoryExpansionModel.of(), CategoryCreateCommandImpl::new);
    }

    CategoryCreateCommandImpl(final ReferenceExpandeableCreateCommandBuilder<Category, CategoryCreateCommand, CategoryDraft, CategoryExpansionModel<Category>> builder) {
        super(builder);
    }
}
