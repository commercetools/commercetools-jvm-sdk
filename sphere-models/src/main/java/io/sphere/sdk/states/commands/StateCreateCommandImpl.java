package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateDraft;
import io.sphere.sdk.states.expansion.StateExpansionModel;

final class StateCreateCommandImpl extends MetaModelCreateCommandImpl<State, StateCreateCommand, StateDraft, StateExpansionModel<State>> implements StateCreateCommand {
    StateCreateCommandImpl(final MetaModelCreateCommandBuilder<State, StateCreateCommand, StateDraft, StateExpansionModel<State>> builder) {
        super(builder);
    }

    StateCreateCommandImpl(final StateDraft body) {
        super(body, StateEndpoint.ENDPOINT, StateExpansionModel.of(), StateCreateCommandImpl::new);
    }
}
