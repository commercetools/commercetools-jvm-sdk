package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.PaymentState;

/**

 {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandTest#changePaymentState()}
 */
public class ChangePaymentState extends UpdateAction<Order> {
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
