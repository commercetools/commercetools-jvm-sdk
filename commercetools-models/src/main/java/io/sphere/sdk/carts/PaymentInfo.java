package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.payments.Payment;

import java.util.List;

@JsonDeserialize(as = PaymentInfoImpl.class)
public interface PaymentInfo {
    static PaymentInfo of(final List<Reference<Payment>> payments) {
        return new PaymentInfoImpl(payments);
    }

    List<Reference<Payment>> getPayments();
}
