package io.sphere.sdk.types.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.TypeDraft;
import io.sphere.sdk.types.expansion.TypeExpansionModel;

/**
 * {@include.example io.sphere.sdk.types.commands.TypeCreateCommandTest#execution()}
 *
 * @see io.sphere.sdk.types.Custom
 */
public interface TypeCreateCommand extends CreateCommand<Type>, MetaModelReferenceExpansionDsl<Type, TypeCreateCommand, TypeExpansionModel<Type>> {

    static TypeCreateCommand of(final TypeDraft typeDraft) {
        return new TypeCreateCommandImpl(typeDraft);
    }
}
