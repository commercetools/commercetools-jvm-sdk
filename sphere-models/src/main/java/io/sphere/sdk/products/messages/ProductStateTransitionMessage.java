package io.sphere.sdk.products.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.states.State;

/**
 * This message is the result of the {@link io.sphere.sdk.products.commands.updateactions.TransitionState} update action.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#transitionState()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.TransitionState
 */
public class ProductStateTransitionMessage {
    public static final String MESSAGE_TYPE = "ProductStateTransition";
    public static final MessageDerivateHint<ProductStateTransitionMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductStateTransitionMessage.class);


    private final Reference<State> state;

    @JsonCreator
    private ProductStateTransitionMessage(final Reference<State> state) {
        this.state = state;
    }

    public Reference<State> getState() {
        return state;
    }
}
