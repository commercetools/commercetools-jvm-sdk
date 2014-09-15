package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;

import java.util.List;

public class CategoryUpdateCommand extends UpdateCommandDslImpl<Category> {
    public CategoryUpdateCommand(final Versioned<Category> versioned, final List<UpdateAction<Category>> updateActions) {
        super(versioned, updateActions, Category.typeReference(), "/categories");
    }
}
