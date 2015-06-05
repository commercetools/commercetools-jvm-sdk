package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class CategoryUpdateCommand extends UpdateCommandDslImpl<Category> {
    private CategoryUpdateCommand(final Versioned<Category> versioned, final List<? extends UpdateAction<Category>> updateActions) {
        super(versioned, updateActions, CategoryEndpoint.ENDPOINT);
    }

    public static CategoryUpdateCommand of(final Versioned<Category> versioned, final UpdateAction<Category> updateAction) {
        return of(versioned, asList(updateAction));
    }

    public static CategoryUpdateCommand of(final Versioned<Category> versioned, final List<? extends UpdateAction<Category>> updateActions) {
        return new CategoryUpdateCommand(versioned, updateActions);
    }
}
