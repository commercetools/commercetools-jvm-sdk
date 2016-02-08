package io.sphere.sdk.payments.expansion;

import io.sphere.sdk.states.expansion.StateExpansionModel;

public interface PaymentStatusExpansionModel<T> {
    StateExpansionModel<T> state();
}
