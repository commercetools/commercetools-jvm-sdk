package io.sphere.sdk.payments.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;

public interface PaymentStatusExpansionModel<T> {
    ExpansionPathContainer<T> state();
}
