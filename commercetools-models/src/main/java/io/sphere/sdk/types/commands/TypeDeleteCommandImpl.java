package io.sphere.sdk.types.commands;

import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.expansion.TypeExpansionModel;

final class TypeDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<Type, TypeDeleteCommand, TypeExpansionModel<Type>> implements TypeDeleteCommand {

    TypeDeleteCommandImpl(final Versioned<Type> versioned) {
        super(versioned, TypeEndpoint.ENDPOINT, TypeExpansionModel.of(), TypeDeleteCommandImpl::new);
    }

    TypeDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<Type, TypeDeleteCommand, TypeExpansionModel<Type>> builder) {
        super(builder);
    }
}
