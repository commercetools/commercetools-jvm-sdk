package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.payments.Payment;

import java.util.List;

final class PaymentInfoImpl extends Base implements PaymentInfo {
    private final List<Reference<Payment>> payments;

    @JsonCreator
    PaymentInfoImpl(final List<Reference<Payment>> payments) {
        this.payments = payments;
    }

    public List<Reference<Payment>> getPayments() {
        return payments;
    }
}
