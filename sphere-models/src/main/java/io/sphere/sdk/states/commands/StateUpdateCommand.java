package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.states.State;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public interface StateUpdateCommand extends UpdateCommandDsl<State, StateUpdateCommand> {
    static StateUpdateCommand of(final Versioned<State> versioned, final UpdateAction<State> updateAction) {
        return of(versioned, asList(updateAction));
    }

    static StateUpdateCommand of(final Versioned<State> versioned, final List<? extends UpdateAction<State>> updateActions) {
        return new StateUpdateCommandImpl(versioned, updateActions);
    }
}
