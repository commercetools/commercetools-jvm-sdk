package io.sphere.sdk.payments.queries;

import io.sphere.sdk.queries.ReferenceOptionalQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.states.State;

public interface PaymentStatusQueryModel<T> {
    StringQuerySortingModel<T> interfaceCode();

    StringQuerySortingModel<T> interfaceText();

    ReferenceOptionalQueryModel<T, State> state();
}
