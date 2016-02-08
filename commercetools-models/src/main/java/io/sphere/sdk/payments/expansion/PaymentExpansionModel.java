package io.sphere.sdk.payments.expansion;

import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.payments.Payment;

import java.util.List;

public interface PaymentExpansionModel<T> extends ExpansionPathContainer<T> {
    CustomerExpansionModel<T> customer();

    PaymentStatusExpansionModel<T> paymentStatus();

    static PaymentExpansionModel<Payment> of() {
        return new PaymentExpansionModelImpl<>();
    }

    static <T> PaymentExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new PaymentExpansionModelImpl<>(parentPath, path);
    }
}
