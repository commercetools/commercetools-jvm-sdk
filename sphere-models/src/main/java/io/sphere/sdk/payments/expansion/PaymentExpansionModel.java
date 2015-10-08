package io.sphere.sdk.payments.expansion;

import io.sphere.sdk.expansion.ExpandedModel;
import io.sphere.sdk.payments.Payment;


public class PaymentExpansionModel<T> extends ExpandedModel<T> {
    PaymentExpansionModel() {
        super();
    }

    public static PaymentExpansionModel<Payment> of() {
        return new PaymentExpansionModel<>();
    }
}
