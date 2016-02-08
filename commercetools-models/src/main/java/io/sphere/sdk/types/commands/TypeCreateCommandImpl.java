package io.sphere.sdk.types.commands;

import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.TypeDraft;
import io.sphere.sdk.types.expansion.TypeExpansionModel;

final class TypeCreateCommandImpl extends MetaModelCreateCommandImpl<Type, TypeCreateCommand, TypeDraft, TypeExpansionModel<Type>> implements TypeCreateCommand {

    TypeCreateCommandImpl(final TypeDraft typeDraft) {
        super(typeDraft, TypeEndpoint.ENDPOINT, TypeExpansionModel.of(), TypeCreateCommandImpl::new);
    }

    TypeCreateCommandImpl(final MetaModelCreateCommandBuilder<Type, TypeCreateCommand, TypeDraft, TypeExpansionModel<Type>> builder) {
        super(builder);
    }
}
