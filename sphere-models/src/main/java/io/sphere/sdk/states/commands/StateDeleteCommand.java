package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.expansion.StateExpansionModel;

/** Deletes a state.

 <p>Example:</p>
 {@include.example io.sphere.sdk.states.commands.StateDeleteCommandTest#execution()}
 */
public interface StateDeleteCommand extends ByIdDeleteCommand<State>, MetaModelExpansionDsl<State, StateDeleteCommand, StateExpansionModel<State>> {
    static StateDeleteCommand of(final Versioned<State> versioned) {
        return new StateDeleteCommandImpl(versioned);
    }
}





