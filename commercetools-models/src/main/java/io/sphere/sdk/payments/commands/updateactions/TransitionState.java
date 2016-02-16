package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Transition to a new state. If there is no state yet, the new state must be an initial state. If there is an existing state, there must be a direct transition from the existing state to the new state.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#transitionState()}
 *
 *  @see Payment
 *  @see io.sphere.sdk.payments.messages.PaymentStatusStateTransitionMessage
 */
public final class TransitionState extends UpdateActionImpl<Payment> {
    @Nullable
    private final Reference<State> state;

    private TransitionState(@Nullable final Reference<State> state) {
        super("transitionState");
        this.state = state;
    }

    public static TransitionState of(final @Nullable Referenceable<State> state) {
        final Reference<State> stateReference =
                Optional.ofNullable(state).map(stateReferenceable -> stateReferenceable.toReference()).orElse(null);
        return new TransitionState(stateReference);
    }

    @Nullable
    public Reference<State> getState() {
        return state;
    }
}
