package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;

import java.util.Collections;
import java.util.List;

/**
 * <p>Updates a category.</p>
 *
 {@doc.gen list actions}
 */
public interface CategoryUpdateCommand extends UpdateCommandDsl<Category, CategoryUpdateCommand>, MetaModelExpansionDsl<Category, CategoryUpdateCommand, CategoryExpansionModel<Category>> {
    static CategoryUpdateCommand of(final Versioned<Category> versioned, final UpdateAction<Category> updateAction) {
        return of(versioned, Collections.singletonList(updateAction));
    }

    static CategoryUpdateCommand of(final Versioned<Category> versioned, final List<? extends UpdateAction<Category>> updateActions) {
        return new CategoryUpdateCommandImpl(versioned, updateActions);
    }

    @Override
    CategoryUpdateCommand withExpansionPaths(final List<ExpansionPath<Category>> expansionPaths);

    @Override
    CategoryUpdateCommand withExpansionPaths(final ExpansionPath<Category> expansionPath);

    @Override
    CategoryUpdateCommand plusExpansionPaths(final List<ExpansionPath<Category>> expansionPaths);

    @Override
    CategoryUpdateCommand plusExpansionPaths(final ExpansionPath<Category> expansionPath);

    @Override
    CategoryUpdateCommand withVersion(final Versioned<Category> versioned);
}
