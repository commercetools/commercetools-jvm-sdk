package io.sphere.sdk.carts;

import io.sphere.sdk.models.Reference;
import io.sphere.sdk.payments.Payment;

import java.util.List;

public interface PaymentInfo {
    static PaymentInfo of(final List<Reference<Payment>> payments) {
        return new PaymentInfoImpl(payments);
    }

    List<Reference<Payment>> getPayments();
}
