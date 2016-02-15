package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.payments.Payment;

import java.util.Optional;

/**
 *
 * This action removes a payment from the PaymentInfo.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#addPayment()}
 *
 * @see Payment
 * @see Cart#getPaymentInfo()
 * @see AddPayment
 */
public final class RemovePayment extends UpdateActionImpl<Cart> {
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
