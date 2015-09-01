package io.sphere.sdk.types.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.TypeDraft;
import io.sphere.sdk.types.expansion.TypeExpansionModel;


public interface TypeCreateCommand extends CreateCommand<Type>, MetaModelExpansionDsl<Type, TypeCreateCommand, TypeExpansionModel<Type>> {

    static TypeCreateCommand of(final TypeDraft typeDraft) {
        return new TypeCreateCommandImpl(typeDraft);
    }
}
