package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.ReturnItem;
import io.sphere.sdk.orders.ReturnPaymentState;

/**
 Sets the return payment state.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#setReturnPaymentState()}
 */
public final class SetReturnPaymentState extends UpdateActionImpl<Order> {
    private final String returnItemId;
    private final ReturnPaymentState paymentState;

    private SetReturnPaymentState(final String returnItemId, final ReturnPaymentState paymentState) {
        super("setReturnPaymentState");
        this.returnItemId = returnItemId;
        this.paymentState = paymentState;
    }

    public String getReturnItemId() {
        return returnItemId;
    }

    public ReturnPaymentState getPaymentState() {
        return paymentState;
    }

    public static SetReturnPaymentState of(final String returnItemId, final ReturnPaymentState paymentState) {
        return new SetReturnPaymentState(returnItemId, paymentState);
    }

    public static SetReturnPaymentState of(final ReturnItem returnItem, final ReturnPaymentState paymentState) {
        return of(returnItem.getId(), paymentState);
    }
}
