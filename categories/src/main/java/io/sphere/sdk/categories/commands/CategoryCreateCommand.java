package io.sphere.sdk.categories.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.NewCategory;
import io.sphere.sdk.commands.Command;
import io.sphere.sdk.commands.CreateCommandImpl;

/**
 * Command to create a category.
 *
 * {@include.example example.CategoryLifecycleExample#createCategory()}
 *
 * For construction of a {@link io.sphere.sdk.categories.NewCategory}, see {@link io.sphere.sdk.categories.NewCategoryBuilder}.
 */
public final class CategoryCreateCommand extends CreateCommandImpl<Category, NewCategory> implements Command<Category> {

    public CategoryCreateCommand(final NewCategory newCategory) {
        super(newCategory);
    }

    @Override
    protected String httpEndpoint() {
        return "/categories";
    }

    @Override
    public TypeReference<Category> typeReference() {
        return Category.typeReference();
    }
}
