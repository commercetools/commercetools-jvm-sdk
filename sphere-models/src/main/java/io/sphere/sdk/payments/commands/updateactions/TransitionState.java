package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;
import java.util.Optional;

public class TransitionState extends UpdateActionImpl<Payment> {
    @Nullable
    private final Reference<State> state;

    private TransitionState(final Reference<State> state) {
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
