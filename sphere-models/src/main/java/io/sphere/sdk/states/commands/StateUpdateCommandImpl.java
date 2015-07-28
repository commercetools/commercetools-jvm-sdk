package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslBuilder;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.states.State;

import java.util.List;


final class StateUpdateCommandImpl extends UpdateCommandDslImpl<State, StateUpdateCommand> implements StateUpdateCommand {
    StateUpdateCommandImpl(final Versioned<State> versioned, final List<? extends UpdateAction<State>> updateActions) {
        super(versioned, updateActions, StateEndpoint.ENDPOINT, StateUpdateCommandImpl::new);
    }

    StateUpdateCommandImpl(final UpdateCommandDslBuilder<State, StateUpdateCommand> builder) {
        super(builder);
    }
}
