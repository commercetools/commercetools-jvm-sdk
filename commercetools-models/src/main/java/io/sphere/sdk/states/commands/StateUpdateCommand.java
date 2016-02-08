package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.expansion.StateExpansionModel;

import java.util.Collections;
import java.util.List;

/**
 {@doc.gen list actions}
 */
public interface StateUpdateCommand extends UpdateCommandDsl<State, StateUpdateCommand>, MetaModelReferenceExpansionDsl<State, StateUpdateCommand, StateExpansionModel<State>> {
    static StateUpdateCommand of(final Versioned<State> versioned, final UpdateAction<State> updateAction) {
        return of(versioned, Collections.singletonList(updateAction));
    }

    static StateUpdateCommand of(final Versioned<State> versioned, final List<? extends UpdateAction<State>> updateActions) {
        return new StateUpdateCommandImpl(versioned, updateActions);
    }
}
