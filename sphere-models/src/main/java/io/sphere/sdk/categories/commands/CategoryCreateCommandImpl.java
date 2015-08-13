package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;

final class CategoryCreateCommandImpl extends MetaModelCreateCommandImpl<Category, CategoryCreateCommand, CategoryDraft, CategoryExpansionModel<Category>> implements CategoryCreateCommand {

    CategoryCreateCommandImpl(final CategoryDraft categoryDraft) {
        super(categoryDraft, CategoryEndpoint.ENDPOINT, CategoryExpansionModel.of(), CategoryCreateCommandImpl::new);
    }

    CategoryCreateCommandImpl(final MetaModelCreateCommandBuilder<Category, CategoryCreateCommand, CategoryDraft, CategoryExpansionModel<Category>> builder) {
        super(builder);
    }
}
