package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.*;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.expansion.StateExpansionModel;

import java.util.List;


final class StateUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<State, StateUpdateCommand, StateExpansionModel<State>> implements StateUpdateCommand {
    StateUpdateCommandImpl(final Versioned<State> versioned, final List<? extends UpdateAction<State>> updateActions) {
        super(versioned, updateActions, StateEndpoint.ENDPOINT, StateUpdateCommandImpl::new, StateExpansionModel.of());
    }

    StateUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<State, StateUpdateCommand, StateExpansionModel<State>> builder) {
        super(builder);
    }
}
