package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateDraft;

final class StateCreateCommandImpl extends CreateCommandImpl<State, StateDraft> implements StateCreateCommand {
    StateCreateCommandImpl(final StateDraft body) {
        super(body, StateEndpoint.ENDPOINT);
    }
}
