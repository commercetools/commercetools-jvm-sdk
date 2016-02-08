package io.sphere.sdk.payments.queries;

import io.sphere.sdk.queries.LocalizedStringQuerySortingModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.StringQuerySortingModel;

final class PaymentMethodInfoQueryModelImpl<T> extends QueryModelImpl<T> implements PaymentMethodInfoQueryModel<T> {
    public PaymentMethodInfoQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<T> method() {
        return stringModel("method");
    }

    @Override
    public StringQuerySortingModel<T> paymentInterface() {
        return stringModel("paymentInterface");
    }

    @Override
    public LocalizedStringQuerySortingModel<T> name() {
        return localizedStringQuerySortingModel("name");
    }
}
