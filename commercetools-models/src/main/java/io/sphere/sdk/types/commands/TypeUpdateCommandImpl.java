package io.sphere.sdk.types.commands;

import io.sphere.sdk.commands.MetaModelUpdateCommandDslImpl;
import io.sphere.sdk.commands.MetaModelUpdateCommandDslBuilder;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.expansion.TypeExpansionModel;

import java.util.List;

final class TypeUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<Type, TypeUpdateCommand, TypeExpansionModel<Type>> implements TypeUpdateCommand {

    TypeUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<Type, TypeUpdateCommand, TypeExpansionModel<Type>> builder) {
        super(builder);
    }

    TypeUpdateCommandImpl(final Versioned<Type> versioned, final List<? extends UpdateAction<Type>> updateActions) {
        super(versioned, updateActions, TypeEndpoint.ENDPOINT, TypeUpdateCommandImpl::new, TypeExpansionModel.of());
    }
}
