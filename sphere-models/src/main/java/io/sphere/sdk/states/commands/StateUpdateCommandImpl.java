package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.states.State;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class StateUpdateCommandImpl extends UpdateCommandDslImpl<State, StateUpdateCommandImpl> {
    private StateUpdateCommandImpl(final Versioned<State> versioned, final List<? extends UpdateAction<State>> updateActions) {
        super(versioned, updateActions, StateEndpoint.ENDPOINT);
    }

    public static StateUpdateCommandImpl of(final Versioned<State> versioned, final UpdateAction<State> updateAction) {
        return of(versioned, asList(updateAction));
    }

    public static StateUpdateCommandImpl of(final Versioned<State> versioned, final List<? extends UpdateAction<State>> updateActions) {
        return new StateUpdateCommandImpl(versioned, updateActions);
    }
}
