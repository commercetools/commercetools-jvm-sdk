package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.expansion.StateExpansionModel;

/** Deletes a state.

 <p>Example:</p>
 {@include.example io.sphere.sdk.states.commands.StateDeleteCommandTest#execution()}
 */
public interface StateDeleteCommand extends MetaModelReferenceExpansionDsl<State, StateDeleteCommand, StateExpansionModel<State>>, DeleteCommand<State> {
    static StateDeleteCommand of(final Versioned<State> versioned) {
        return new StateDeleteCommandImpl(versioned);
    }
}





