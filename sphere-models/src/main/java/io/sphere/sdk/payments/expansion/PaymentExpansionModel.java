package io.sphere.sdk.payments.expansion;

import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.payments.Payment;

public interface PaymentExpansionModel<T> {
    CustomerExpansionModel<T> customer();

    PaymentStatusExpansionModel<T> paymentStatus();


    static PaymentExpansionModel<Payment> of() {
        return new PaymentExpansionModelImpl<>();
    }
}
