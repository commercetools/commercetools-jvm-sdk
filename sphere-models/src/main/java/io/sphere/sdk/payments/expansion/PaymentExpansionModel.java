package io.sphere.sdk.payments.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.payments.Payment;


public class PaymentExpansionModel<T> extends ExpansionModel<T> {
    PaymentExpansionModel() {
        super();
    }

    public static PaymentExpansionModel<Payment> of() {
        return new PaymentExpansionModel<>();
    }

    public ExpansionPathContainer<T> customer() {
        return expansionPath("customer");
    }

    public PaymentStatusExpansionModel<T> paymentStatus() {
        return new PaymentStatusExpansionModel<>(buildPathExpression(), "paymentStatus");
    }
}
