package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.states.State;

final class StateDeleteCommandImpl extends ByIdDeleteCommandImpl<State> {
    StateDeleteCommandImpl(final Versioned<State> versioned) {
        super(versioned, StateEndpoint.ENDPOINT);
    }
}





