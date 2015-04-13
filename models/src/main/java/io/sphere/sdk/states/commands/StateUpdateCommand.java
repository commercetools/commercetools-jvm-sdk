package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.states.State;

import java.util.List;

/**
 {@doc.gen list actions}
 */
public class StateUpdateCommand extends UpdateCommandDslImpl<State> {
    private StateUpdateCommand(final Versioned<State> versioned, final List<? extends UpdateAction<State>> updateActions) {
        super(versioned, updateActions, StateEndpoint.ENDPOINT);
    }

    public static StateUpdateCommand of(final Versioned<State> versioned, final List<? extends UpdateAction<State>> updateActions) {
        return new StateUpdateCommand(versioned, updateActions);
    }
}
