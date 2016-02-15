package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.PaymentState;

/**
 Changes the payment state.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#changePaymentState()}
 */
public final class ChangePaymentState extends UpdateActionImpl<Order> {
    private final PaymentState paymentState;

    private ChangePaymentState(final PaymentState paymentState) {
        super("changePaymentState");
        this.paymentState = paymentState;
    }

    public static ChangePaymentState of(final PaymentState paymentState) {
        return new ChangePaymentState(paymentState);
    }

    public PaymentState getPaymentState() {
        return paymentState;
    }
}
