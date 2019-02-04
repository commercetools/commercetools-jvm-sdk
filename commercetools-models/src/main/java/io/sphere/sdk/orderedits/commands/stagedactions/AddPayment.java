package io.sphere.sdk.orderedits.commands.stagedactions;

import io.sphere.sdk.commands.StagedUpdateActionBase;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.payments.Payment;

import java.util.Optional;

public final class AddPayment extends StagedUpdateActionBase<OrderEdit> {

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