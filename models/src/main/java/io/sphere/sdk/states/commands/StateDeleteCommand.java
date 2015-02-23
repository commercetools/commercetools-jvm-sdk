package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.DeleteByIdCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.states.State;

/** Deletes a state.

 <p>Example:</p>
 {@include.example io.sphere.sdk.states.commands.StateDeleteCommandTest#execution()}
 */
public class StateDeleteCommand extends DeleteByIdCommandImpl<State> {
    private StateDeleteCommand(final Versioned<State> versioned) {
        super(versioned, StateEndpoint.ENDPOINT);
    }

    public static DeleteCommand<State> of(final Versioned<State> versioned) {
        return new StateDeleteCommand(versioned);
    }
}





