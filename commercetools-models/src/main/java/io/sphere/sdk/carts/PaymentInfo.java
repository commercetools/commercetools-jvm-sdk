package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.payments.Payment;

import java.util.List;

public final class PaymentInfo extends Base {
    private final List<Reference<Payment>> payments;

    @JsonCreator
    private PaymentInfo(final List<Reference<Payment>> payments) {
        this.payments = payments;
    }

    public static PaymentInfo of(final List<Reference<Payment>> payments) {
        return new PaymentInfo(payments);
    }

    public List<Reference<Payment>> getPayments() {
        return payments;
    }
}
