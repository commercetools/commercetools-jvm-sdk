package io.sphere.sdk.carts.commands.updateactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.commands.UpdateAction;

import java.util.Optional;

/**
 Decreases the quantity of the given line item. If after the update the quantity of the line item is not greater than 0 or the quantity is not specified, the line item is removed from the cart.

 {@include.example io.sphere.sdk.carts.CartIntegrationTest#removeLineItemUpdateAction()}
 */
public class RemoveLineItem extends UpdateAction<Cart> {
    private final String lineItemId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Optional<Integer> quantity;

    private RemoveLineItem(final String lineItemId, final Optional<Integer> quantity) {
        super("removeLineItem");
        this.lineItemId = lineItemId;
        this.quantity = quantity;
    }

    public static RemoveLineItem of(final String lineItemId, final Optional<Integer> quantity) {
        return new RemoveLineItem(lineItemId, quantity);
    }

    public static RemoveLineItem of(final String lineItemId, final Integer quantity) {
        return of(lineItemId, Optional.of(quantity));
    }

    public static RemoveLineItem of(final String lineItemId) {
        return of(lineItemId, Optional.empty());
    }

    public static RemoveLineItem of(final LineItem lineItem, final Optional<Integer> quantity) {
        return of(lineItem.getId(), quantity);
    }

    public static RemoveLineItem of(final LineItem lineItem, final Integer quantity) {
        return of(lineItem.getId(), Optional.of(quantity));
    }

    public static RemoveLineItem of(final LineItem lineItem) {
        return of(lineItem.getId(), Optional.empty());
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public Optional<Integer> getQuantity() {
        return quantity;
    }
}
