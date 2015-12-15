package io.sphere.sdk.types.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.expansion.TypeExpansionModel;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 {@doc.gen list actions}
 */
public interface TypeUpdateCommand extends UpdateCommandDsl<Type, TypeUpdateCommand>, MetaModelExpansionDsl<Type, TypeUpdateCommand, TypeExpansionModel<Type>> {
    static TypeUpdateCommand of(final Versioned<Type> versioned, final UpdateAction<Type> updateAction) {
        return of(versioned, Collections.singletonList(updateAction));
    }

    static TypeUpdateCommand of(final Versioned<Type> versioned, final List<? extends UpdateAction<Type>> updateActions) {
        return new TypeUpdateCommandImpl(versioned, updateActions);
    }

    @Override
    TypeUpdateCommand withExpansionPaths(final List<ExpansionPath<Type>> expansionPaths);

    @Override
    TypeUpdateCommand withExpansionPaths(final ExpansionPath<Type> expansionPath);

    @Override
    TypeUpdateCommand plusExpansionPaths(final List<ExpansionPath<Type>> expansionPaths);

    @Override
    TypeUpdateCommand plusExpansionPaths(final ExpansionPath<Type> expansionPath);

    @Override
    TypeUpdateCommand withVersion(final Versioned<Type> versioned);
}
