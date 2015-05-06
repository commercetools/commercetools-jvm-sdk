package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.states.State;

import java.time.Instant;
import java.util.Optional;

/**
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandTest#transitionCustomLineItemState()}
 */
public class TransitionCustomLineItemState extends TransitionLineItemLikeState {

    private final String customLineItemId;

    private TransitionCustomLineItemState(final String customLineItemId, final long quantity, final Referenceable<State> fromState, final Referenceable<State> toState,
                                          final Optional<Instant> actualTransitionDate) {
        super("transitionCustomLineItemState", quantity, actualTransitionDate, toState.toReference(), fromState.toReference());
        this.customLineItemId = customLineItemId;
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }

    public static TransitionCustomLineItemState of(final String customLineItemId, final long quantity,
                                             final Referenceable<State> fromState, final Referenceable<State> toState,
                                             final Optional<Instant> actualTransitionDate) {
        return new TransitionCustomLineItemState(customLineItemId, quantity, fromState, toState, actualTransitionDate);
    }

    public static UpdateAction<Order> of(final CustomLineItem lineItem, final long quantity,
                                         final Referenceable<State> fromState, final Referenceable<State> toState,
                                         final Instant actualTransitionDate) {
        return of(lineItem.getId(), quantity, fromState, toState, Optional.of(actualTransitionDate));
    }

    public static UpdateAction<Order> of(final CustomLineItem lineItem, final long quantity,
                                         final Referenceable<State> fromState, final Referenceable<State> toState) {
        return of(lineItem.getId(), quantity, fromState, toState, Optional.<Instant>empty());
    }
}

