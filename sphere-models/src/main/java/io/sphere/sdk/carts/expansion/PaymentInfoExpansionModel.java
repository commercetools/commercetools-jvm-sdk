package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.payments.expansion.PaymentExpansionModel;

public interface PaymentInfoExpansionModel<T> {
    PaymentExpansionModel<T> payments();
}
