package io.sphere.sdk.types.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.expansion.TypeExpansionModel;


public interface TypeDeleteCommand extends ByIdDeleteCommand<Type>, MetaModelExpansionDsl<Type, TypeDeleteCommand, TypeExpansionModel<Type>> {

    static TypeDeleteCommand of(final Versioned<Type> versioned) {
        return new TypeDeleteCommandImpl(versioned);
    }
}
