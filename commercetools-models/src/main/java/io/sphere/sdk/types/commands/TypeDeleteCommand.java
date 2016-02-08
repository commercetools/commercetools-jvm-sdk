package io.sphere.sdk.types.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.expansion.TypeExpansionModel;


public interface TypeDeleteCommand extends MetaModelReferenceExpansionDsl<Type, TypeDeleteCommand, TypeExpansionModel<Type>>, DeleteCommand<Type> {

    static TypeDeleteCommand of(final Versioned<Type> versioned) {
        return new TypeDeleteCommandImpl(versioned);
    }
}
