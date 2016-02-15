package io.sphere.sdk.states.commands;

import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateDraft;
import io.sphere.sdk.states.expansion.StateExpansionModel;

/** Creates a state.

 <p>Example:</p>
 {@include.example io.sphere.sdk.states.commands.StateCreateCommandIntegrationTest#execution()}

 @see io.sphere.sdk.states.StateDraftBuilder
 */
public interface StateCreateCommand extends DraftBasedCreateCommand<State, StateDraft>, MetaModelReferenceExpansionDsl<State, StateCreateCommand, StateExpansionModel<State>> {
    static StateCreateCommand of(final StateDraft draft) {
        return new StateCreateCommandImpl(draft);
    }
}
