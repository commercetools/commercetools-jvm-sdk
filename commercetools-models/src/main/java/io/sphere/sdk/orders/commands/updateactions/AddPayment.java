package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.payments.Payment;

import java.util.Optional;

/**
 *
 * This action adds a payment to the PaymentInfo. The payment must not be assigned to another Order or active Cart yet.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#addPayment()}
 *
 * @see Payment
 * @see Order#getPaymentInfo()
 * @see RemovePayment
 */
public final class AddPayment extends UpdateActionImpl<Order> {
    private final Reference<Payment> payment;

    private AddPayment(final Reference<Payment> payment) {
        super("addPayment");
        this.payment = payment;
    }

    public static AddPayment of(final Referenceable<Payment> payment) {
        return new AddPayment(Optional.ofNullable(payment).map(Referenceable::toReference).orElse(null));
    }

    public Reference<Payment> getPayment() {
        return payment;
    }
}
