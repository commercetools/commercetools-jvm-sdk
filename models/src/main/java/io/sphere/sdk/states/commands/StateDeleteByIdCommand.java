package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.DeleteByIdCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.states.State;

/** Deletes a state.

 <p>Example:</p>
 {@include.example io.sphere.sdk.states.StateIntegrationTest#deleteStateById()}
 */
public class StateDeleteByIdCommand extends DeleteByIdCommandImpl<State> {
    private StateDeleteByIdCommand(final Versioned<State> versioned) {
        super(versioned, StateEndpoint.ENDPOINT);
    }

    public static StateDeleteByIdCommand of(final Versioned<State> versioned) {
        return new StateDeleteByIdCommand(versioned);
    }
}





