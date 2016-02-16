package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.payments.Payment;

import java.util.Optional;

/**
 *
 * This action removes a payment from the PaymentInfo.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#addPayment()}
 *
 * @see Payment
 * @see Order#getPaymentInfo()
 * @see AddPayment
 */
public final class RemovePayment extends UpdateActionImpl<Order> {
    private final Reference<Payment> payment;

    private RemovePayment(final Reference<Payment> payment) {
        super("removePayment");
        this.payment = payment;
    }

    public static RemovePayment of(final Referenceable<Payment> payment) {
        return new RemovePayment(Optional.ofNullable(payment).map(Referenceable::toReference).orElse(null));
    }

    public Reference<Payment> getPayment() {
        return payment;
    }
}
