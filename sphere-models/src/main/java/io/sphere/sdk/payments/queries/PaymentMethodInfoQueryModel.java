package io.sphere.sdk.payments.queries;

import io.sphere.sdk.queries.LocalizedStringQuerySortingModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

public interface PaymentMethodInfoQueryModel<T> {
    StringQuerySortingModel<T> paymentInterface();

    StringQuerySortingModel<T> method();

    LocalizedStringQuerySortingModel<T> name();
}
