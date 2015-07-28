package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateDraft;

/** Creates a state.

 <p>Example:</p>
 {@include.example io.sphere.sdk.states.commands.StateCreateCommandTest#execution()}

 @see io.sphere.sdk.states.StateDraftBuilder
 */
public class StateCreateCommandImpl extends CreateCommandImpl<State, StateDraft> {
    private StateCreateCommandImpl(final StateDraft body) {
        super(body, StateEndpoint.ENDPOINT);
    }

    public static StateCreateCommandImpl of(final StateDraft draft) {
        return new StateCreateCommandImpl(draft);
    }
}
