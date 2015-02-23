package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateDraft;

/** Creates a state.

 <p>Example:</p>
 {@include.example io.sphere.sdk.states.StateIntegrationTest#createState()}

 @see io.sphere.sdk.states.StateDraftBuilder
 */
public class StateCreateCommand extends CreateCommandImpl<State, StateDraft> {
    private StateCreateCommand(final StateDraft body) {
        super(body, StateEndpoint.ENDPOINT);
    }

    public static StateCreateCommand of(final StateDraft draft) {
        return new StateCreateCommand(draft);
    }
}
