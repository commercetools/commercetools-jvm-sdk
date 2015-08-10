package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandBuilder;
import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandImpl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateDraft;
import io.sphere.sdk.states.expansion.StateExpansionModel;

final class StateCreateCommandImpl extends ReferenceExpandeableCreateCommandImpl<State, StateCreateCommand, StateDraft, StateExpansionModel<State>> implements StateCreateCommand {
    StateCreateCommandImpl(final ReferenceExpandeableCreateCommandBuilder<State, StateCreateCommand, StateDraft, StateExpansionModel<State>> builder) {
        super(builder);
    }

    StateCreateCommandImpl(final StateDraft body) {
        super(body, StateEndpoint.ENDPOINT, StateExpansionModel.of(), StateCreateCommandImpl::new);
    }
}
