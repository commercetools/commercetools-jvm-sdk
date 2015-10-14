package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.states.State;

/**
 * This message is the result of the {@link io.sphere.sdk.products.commands.updateactions.TransitionState} update action.
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandTest#transitionState()}
 *
 * @see io.sphere.sdk.orders.Order
 * @see Order#getState()
 * @see io.sphere.sdk.orders.commands.updateactions.TransitionState
 */
public class OrderStateTransitionMessage {
    public static final String MESSAGE_TYPE = "OrderStateTransition";
    public static final MessageDerivateHint<OrderStateTransitionMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE,
                    new TypeReference<PagedQueryResult<OrderStateTransitionMessage>>() {
                    },
                    new TypeReference<OrderStateTransitionMessage>() {
                    }
            );


    private final Reference<State> state;

    @JsonCreator
    private OrderStateTransitionMessage(final Reference<State> state) {
        this.state = state;
    }

    public Reference<State> getState() {
        return state;
    }
}
