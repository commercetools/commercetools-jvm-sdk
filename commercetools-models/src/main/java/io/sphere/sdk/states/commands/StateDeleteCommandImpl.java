package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.expansion.StateExpansionModel;

final class StateDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<State, StateDeleteCommand, StateExpansionModel<State>> implements StateDeleteCommand {
    StateDeleteCommandImpl(final Versioned<State> versioned) {
        super(versioned, StateEndpoint.ENDPOINT, StateExpansionModel.of(), StateDeleteCommandImpl::new);
    }

    StateDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<State, StateDeleteCommand, StateExpansionModel<State>> builder) {
        super(builder);
    }
}





