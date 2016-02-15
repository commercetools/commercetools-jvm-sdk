package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.states.State;

import java.time.ZonedDateTime;

/**
 * Change the state of a Custom Line Item according to allowed transitions.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#transitionCustomLineItemState()}
 */
public final class TransitionCustomLineItemState extends TransitionLineItemLikeState {

    private final String customLineItemId;

    private TransitionCustomLineItemState(final String customLineItemId, final Long quantity, final Referenceable<State> fromState, final Referenceable<State> toState,
                                          final ZonedDateTime actualTransitionDate) {
        super("transitionCustomLineItemState", quantity, actualTransitionDate, toState.toReference(), fromState.toReference());
        this.customLineItemId = customLineItemId;
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }

    public static TransitionCustomLineItemState of(final String customLineItemId, final long quantity,
                                             final Referenceable<State> fromState, final Referenceable<State> toState,
                                             final ZonedDateTime actualTransitionDate) {
        return new TransitionCustomLineItemState(customLineItemId, quantity, fromState, toState, actualTransitionDate);
    }

    public static UpdateAction<Order> of(final CustomLineItem lineItem, final long quantity,
                                         final Referenceable<State> fromState, final Referenceable<State> toState,
                                         final ZonedDateTime actualTransitionDate) {
        return of(lineItem.getId(), quantity, fromState, toState, actualTransitionDate);
    }

    public static UpdateAction<Order> of(final CustomLineItem lineItem, final long quantity,
                                         final Referenceable<State> fromState, final Referenceable<State> toState) {
        return of(lineItem.getId(), quantity, fromState, toState, null);
    }
}

