package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.states.State;

/** Deletes a state.

 <p>Example:</p>
 {@include.example io.sphere.sdk.states.commands.StateDeleteCommandTest#execution()}
 */
public interface StateDeleteCommand extends ByIdDeleteCommand<State> {
    static DeleteCommand<State> of(final Versioned<State> versioned) {
        return new StateDeleteCommandImpl(versioned);
    }
}





