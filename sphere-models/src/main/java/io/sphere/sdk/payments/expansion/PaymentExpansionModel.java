package io.sphere.sdk.payments.expansion;

import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.payments.Payment;


public final class PaymentExpansionModel<T> extends ExpansionModel<T> {
    PaymentExpansionModel() {
        super();
    }

    public static PaymentExpansionModel<Payment> of() {
        return new PaymentExpansionModel<>();
    }

    public CustomerExpansionModel<T> customer() {
        return CustomerExpansionModel.of(buildPathExpression(), "customer");
    }

    public PaymentStatusExpansionModel<T> paymentStatus() {
        return new PaymentStatusExpansionModel<>(buildPathExpression(), "paymentStatus");
    }
}
