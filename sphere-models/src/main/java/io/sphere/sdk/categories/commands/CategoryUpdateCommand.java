package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.models.Versioned;

import java.util.Collections;
import java.util.List;

/**
 {@doc.gen list actions}
 */
public interface CategoryUpdateCommand extends UpdateCommandDsl<Category, CategoryUpdateCommand> {
    static CategoryUpdateCommand of(final Versioned<Category> versioned, final UpdateAction<Category> updateAction) {
        return of(versioned, Collections.singletonList(updateAction));
    }

    static CategoryUpdateCommand of(final Versioned<Category> versioned, final List<? extends UpdateAction<Category>> updateActions) {
        return new CategoryUpdateCommandImpl(versioned, updateActions);
    }
}
