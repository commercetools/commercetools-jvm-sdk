package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.payments.Payment;

import java.util.Optional;

public final class RemovePayment extends OrderEditStagedUpdateActionBase {

    private final Reference<Payment> payment;

    @JsonCreator
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